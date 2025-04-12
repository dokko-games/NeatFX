package com.dokko.neatfx.engine.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class NeatFiles {
    /**
     * Gets the size of a path, in bytes
     * @param path the path
     * @return the size of the path as a long
     */
    public static long size(Path path) {

        final AtomicLong size = new AtomicLong(0);

        try {
            Files.walkFileTree(path, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {

                    size.addAndGet(attrs.size());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {

                    System.out.println("skipped: " + file + " (" + exc + ")");
                    // Skip folders that can't be traversed
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {

                    if (exc != null)
                        System.out.println("had trouble traversing: " + dir + " (" + exc + ")");
                    // Ignore errors traversing a folder
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            throw new AssertionError("walkFileTree will not throw IOException if the FileVisitor does not");
        }

        return size.get();
    }

    /**
     * Deletes the file, and if it is a folder it deletes its children
     * @param f the file to delete
     */
    public static boolean delete(File f) {
        boolean state = true;
        if(f.isDirectory()){
            if(f.listFiles() == null)return f.delete();
            for(File f2 : Objects.requireNonNull(f.listFiles())){
                state = state && delete(f2);
            }
        }
        state = state && f.delete();
        return state;
    }
}
