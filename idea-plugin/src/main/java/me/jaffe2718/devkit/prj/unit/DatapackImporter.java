package me.jaffe2718.devkit.prj.unit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public interface DatapackImporter {

    /**
     * Extract all items in the datapack to the project directory.
     * @param packFilePth The path to the datapack zip file.
     * @param dstPth The path to the project root directory.
     * */
    default void extractDatapack(String packFilePth, String dstPth) {
        File packFile = new File(packFilePth);
        Path dstPath = Path.of(dstPth);
        try (FileInputStream fis = new FileInputStream(packFile);
             ZipInputStream zis = new ZipInputStream(fis)) {
            ZipEntry ze;
            while ((ze = zis.getNextEntry()) != null) {
                File dstFile = dstPath.resolve(ze.getName()).toFile();
                if (ze.isDirectory()) {
                    assert dstFile.mkdirs();
                } else {
                    dstFile.getParentFile().mkdirs();
                    dstFile.createNewFile();
                    System.out.println("Extracting " + ze.getName() + "...");
                    FileOutputStream fos = new FileOutputStream(dstFile);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        // write the contents to the file
                        fos.write(buffer, 0, len);
                    }
                    fos.flush();
                    fos.close();
                }
            }
        } catch (IOException ignored) {
            System.err.println("Failed to extract datapack.");
        }

    }

    /**
     * Validates the zip file to ensure it is a valid datapack.
     * @param packPth The path to the datapack zip file.
     * @return Whether the datapack is valid.
     * */
    default boolean validateDatapack(String packPth) {
        File packFile = new File(packPth);
        if (!packFile.getName().endsWith(".zip") || !packFile.exists() || !packFile.isFile()) {
            // 1. check if the packPth is a valid zip file
            return false;
        } else {
            // 2. check if the zip file contains `pack.mcmeta` file and `data` directory
            try (FileInputStream fis = new FileInputStream(packFile);
                 ZipInputStream zis = new ZipInputStream(fis)){
                ZipEntry ze;
                boolean hasPackMeta = false;
                boolean hasDataDir = false;
                while ((ze = zis.getNextEntry()) != null
                        && !(hasPackMeta && hasDataDir)) {
                    if (ze.getName().equals("pack.mcmeta")) {
                        hasPackMeta = true;
                    } else if (ze.getName().startsWith("data/")) {
                        hasDataDir = true;
                    }
                }
                return hasPackMeta && hasDataDir;
            } catch (IOException e) {
                return false;
            }
        }
    }
}
