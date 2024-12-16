package com.app.game.tetris.daoserviceImpl;

import com.app.game.tetris.daoservice.DaoMongoService;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.GridFSUploadStream;
import com.mongodb.client.gridfs.model.GridFSDownloadOptions;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Service
public class DaoMongo implements DaoMongoService {

    @Value("${mongodbPath}")
    String mongodbPath;

    @Value("${shotsPath}")
    String shotsPath;

    @Value("${mongoPrepareShotsPath}")
    String mongoPrepareShotsPath;

    @Value("${mongoUri}")
    String mongoUri;

    @Override
    public void runMongoServer() {
        try {
            Runtime.getRuntime().exec(new String[]{mongodbPath});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void prepareMongoDB() {
        List<String> list = new ArrayList<>();
        list.add("Oswaldo");
        list.add("Tommy");
        list.add("Dunny");
        list.add("Bonny");
        list.add("Ira");
        list.add("Wolfy");
        list.forEach(playerName -> {
            fillMongoDB(playerName, playerName);
            fillMongoDB(playerName + "deskTopSnapShotBest", playerName + "deskTopSnapShotBest");
            fillMongoDB(playerName + "deskTopSnapShot", playerName + "deskTopSnapShot");
        });
    }

    @Override
    public void prepareMongoDBForNewPLayer(String playerName) {
        fillMongoDB("Player", playerName);
        fillMongoDB("PlayerdeskTopSnapShotBest", playerName + "deskTopSnapShotBest");
        fillMongoDB("PlayerdeskTopSnapShot", playerName + "deskTopSnapShot");
    }

    @Override
    public boolean isFilePresentInMongoDB(String fileName) {
        String uri = mongoUri;
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("shopDB");
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("filename", fileName + ".jpg");
        return database.getCollection("fs.files").find(whereQuery).cursor().hasNext();
    }

    @Override
    public boolean isMongoDBNotEmpty() {
        String uri = mongoUri;
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("shopDB");
        return database.getCollection("fs.files").countDocuments() > 0;
    }

    @Override
    public void cleanMongodb(String playerName, String fileName) {
        String uri = mongoUri;
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("shopDB");
        GridFSBucket gridFSBucket = GridFSBuckets.create(database);
        GridFSFindIterable gridFSFile = gridFSBucket.find(Filters.eq("filename", playerName + fileName + ".jpg"));
        while (gridFSFile.cursor().hasNext()) {
            gridFSBucket.delete(gridFSFile.cursor().next().getId());
        }
        mongoClient.close();
    }

    @Override
    public void loadSnapShotIntoMongodb(String playerName, String fileName) {
        String uri = mongoUri;
        String pathToShots = System.getProperty("user.dir") + shotsPath;
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("shopDB");
        GridFSBucket gridFSBucket = GridFSBuckets.create(database);
        byte[] data = new byte[0];
        try {
            data = Files.readAllBytes(Path.of(pathToShots + fileName + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        GridFSUploadOptions options = new GridFSUploadOptions()
                .chunkSizeBytes(1048576)
                .metadata(new Document("type", "jpg"));
        try (GridFSUploadStream uploadStream = gridFSBucket.openUploadStream(playerName + fileName + ".jpg", options)) {
            // Writes file data to the GridFS upload stream
            uploadStream.write(data);
            uploadStream.flush();
            // Prints the "_id" value of the uploaded file
            System.out.println("The file id of the uploaded file is: " + uploadStream.getObjectId().toHexString());
// Prints a message if any exceptions occur during the upload process
        } catch (Exception e) {
            System.err.println("The file upload failed: " + e);
        }
        Bson query = Filters.eq("metadata.type", "jpg");
        Bson sort = Sorts.ascending("filename");
// Retrieves 5 documents in the bucket that match the filter and prints metadata
        gridFSBucket.find(query)
                .sort(sort)
                .limit(5)
                .forEach(new Consumer<GridFSFile>() {
                    @Override
                    public void accept(final GridFSFile gridFSFile) {
                        System.out.println(gridFSFile);
                    }
                });
        // Now you can work with the 'database' object to perform CRUD operations.
        // Don't forget to close the MongoClient when you're done.
        mongoClient.close();
    }

    @Override
    public void makeDesktopSnapshot(String fileNameDetail) {
        String pathToShots = System.getProperty("user.dir") + shotsPath;
        String format = "jpg";
        String fileName = pathToShots + fileNameDetail + "." + format;

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
            Page page = browser.newPage();
            page.navigate("http://localhost:8080/6");

            page.getByLabel("username").fill("user");
            page.getByLabel("password").fill("mas");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Sign in"))
                    .click();
            //   page.waitForURL("http://localhost:8080/6");
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get(fileName)));
        }
    }

    @Override
    public byte[] loadByteArrayFromMongodb(String playerName, String fileName) {
        String uri = mongoUri;
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("shopDB");
        GridFSBucket gridFSBucket = GridFSBuckets.create(database);
        GridFSDownloadOptions downloadOptions = new GridFSDownloadOptions().revision(0);
        byte[] imagenEnBytes = new byte[16384];
// Downloads a file to an output stream
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream();) {
            if (fileName.equals("mugShot")) gridFSBucket.downloadToStream(playerName + ".jpg", buffer, downloadOptions);
            else gridFSBucket.downloadToStream(playerName + fileName + ".jpg", buffer, downloadOptions);
            imagenEnBytes = buffer.toByteArray();
            buffer.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mongoClient.close();
        return imagenEnBytes;
    }

    private void fillMongoDB(String fileNameOnPC, String fileNameINDB) {
        String uri = mongoUri;
        String pathToImageMongoPreparedShots = System.getProperty("user.dir") + mongoPrepareShotsPath;
        MongoClient mongoClient = MongoClients.create(uri);
        MongoDatabase database = mongoClient.getDatabase("shopDB");
        GridFSBucket gridFSBucket = GridFSBuckets.create(database);
        byte[] data = new byte[0];
        try {
            data = Files.readAllBytes(Path.of(pathToImageMongoPreparedShots + fileNameOnPC + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        GridFSUploadOptions options = new GridFSUploadOptions()
                .chunkSizeBytes(1048576)
                .metadata(new Document("type", "jpg"));
        try (GridFSUploadStream uploadStream = gridFSBucket.openUploadStream(fileNameINDB + ".jpg", options)) {
            // Writes file data to the GridFS upload stream
            uploadStream.write(data);
            uploadStream.flush();
            // Prints the "_id" value of the uploaded file
            System.out.println("The file id of the uploaded file is: " + uploadStream.getObjectId().toHexString());
// Prints a message if any exceptions occur during the upload process
        } catch (Exception e) {
            System.err.println("The file upload failed: " + e);
        }
        Bson query = Filters.eq("metadata.type", "jpg");
        Bson sort = Sorts.ascending("filename");
// Retrieves 5 documents in the bucket that match the filter and prints metadata
        gridFSBucket.find(query)
                .sort(sort)
                .limit(5)
                .forEach(new Consumer<GridFSFile>() {
                    @Override
                    public void accept(final GridFSFile gridFSFile) {
                        System.out.println(gridFSFile);
                    }
                });
        // Now you can work with the 'database' object to perform CRUD operations.
        // Don't forget to close the MongoClient when you're done.
        mongoClient.close();
    }
}
