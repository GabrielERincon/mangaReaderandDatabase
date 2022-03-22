package com.example.mangareaderapp;


import com.github.cliftonlabs.json_simple.JsonKey;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.DigestInputStream;

public class TestMangaDex {

    enum Keys implements JsonKey {
        RESULT("result"),
        DATA("data");

        private final Object value;

        Keys(final Object value) {
            this.value = value;
        }

        @Override
        public String getKey() {
            return this.name().toLowerCase();
        }

        @Override
        public Object getValue() {
            return this.value;
        }
    }

    @Test
    public void Test01() {
        MangaDex mangaDex = new MangaDex();
        List<Manga> mangas = mangaDex.search_manga();
        ReadableByteChannel readChannel;

        for (Manga manga : mangas) {
            System.out.println("Manga: " + manga);
        }

        mangaDex.get_cover_info(mangas);
        // TODO Add some assert here
        for (Manga manga : mangas) {
            System.out.println("Manga: " + manga);
            for (MangaCover cover : manga.getCovers()) {
                System.out.println("\tCover:" + cover);
             }
        }

        MangaCover cover = mangas.get(0).getCovers().get(0);
        readChannel = mangaDex.stream_cover(cover);

        System.out.println("Preparing to write " + cover);
        try {
            FileOutputStream fileOS = new FileOutputStream(cover.getFileName());
            FileChannel writeChannel = fileOS.getChannel();
            writeChannel.transferFrom(readChannel, 0, Long.MAX_VALUE);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error storing the cover file contents: " +
                    e.getMessage());
        }
        Path path = Paths.get(cover.getFileName());
        Assert.assertTrue(Files.exists(path));

        /* Verify that the image contents is correct. Calculating the MD5 sum of the file contents
        * is better than just checking the file size.*/
        String md5sum;
        try {
            md5sum = md5sum(path.toFile());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Error reading the cover image file just created: " +
                    e.getMessage());
        }
        System.out.println("md5sum(" + cover.getFileName() + ")=" + md5sum);
        //Assert.assertTrue(md5sum.equals("8f7cf401abd3873c93f09a13b98536fd"));
    }
    /* Adapted from
     * https://github.com/abrensch/brouter/blob/master/brouter-mapaccess/src/main/java/btools/mapaccess/Rd5DiffManager.java */
    public static String md5sum( File f ) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(f));
        DigestInputStream dis = new DigestInputStream(bis, md);
        byte[] buf = new byte[8192];
        for (; ; ) {
            int len = dis.read(buf);
            if (len <= 0) break;
        }
        dis.close();
        byte[] bytes = md.digest();

        StringBuilder md5sum = new StringBuilder();
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xff;
            md5sum.append(hexChar(v >>> 4)).append(hexChar(v & 0xf));
        }
        return md5sum.toString();
    }

    private static char hexChar( int c ) {
        return (char) ( c > 9 ? 'a' + (c - 10) : '0' + c );
    }
}
