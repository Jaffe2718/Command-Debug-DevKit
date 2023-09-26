package me.jaffe2718.devkit.prj.unit;

import com.intellij.openapi.project.Project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class DatapackGenerator {
    private Project project;

    public DatapackGenerator(Project project) {
        this.project = project;
    }

    public CheckState generate() {
        Path prjRoot = Path.of(Objects.requireNonNull(this.project.getBasePath()));  // find the project's root dir
        File packMetaPath = prjRoot.resolve("pack.mcmeta").toFile();   // find the pack.mcmeta file
        File dataDirPath = prjRoot.resolve("data").toFile();           // find `data` dir
        File packPngPath = prjRoot.resolve("pack.png").toFile();       // find pack.png
        Path buildDirPath = prjRoot.resolve("build");                  // find `build` dir
        String pkName = this.project.getName() + ".zip";                     // the name of the zip file

        // check if the pack.mcmeta file and data dir exist
        if (!packMetaPath.exists() || !dataDirPath.exists()) {
            return CheckState.INVALID;
        } else {
            buildDirPath.toFile().mkdirs();
            try (FileOutputStream fos = new FileOutputStream(buildDirPath.resolve(pkName).toFile());
                 ZipOutputStream zos = new ZipOutputStream(fos)) {
                // add pack.mcmeta
                this.addFileToZip(packMetaPath, "pack.mcmeta", zos);
                this.addDirToZip(dataDirPath, "data", zos);
                if (!packPngPath.exists()) {
                    zos.flush();
                    return CheckState.VALID_WITH_WARNINGS;
                }
                else {
                    this.addFileToZip(packPngPath, "pack.png", zos);
                    zos.flush();
                    return CheckState.VALID;
                }
            } catch (IOException e) {
                return CheckState.INVALID;
            }
        }
    }

    private void addFileToZip(File srcFile, String innerPth, ZipOutputStream zos) throws IOException {
        zos.putNextEntry(new ZipEntry(innerPth));
        FileInputStream fis = new FileInputStream(srcFile);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = fis.read(buffer)) > 0) {
            zos.write(buffer, 0, len);
        }
    }

    private void addDirToZip(File srcPth, String innerPth, ZipOutputStream zos) throws IOException {
        if (srcPth.isDirectory()) {
            for (File file : Objects.requireNonNull(srcPth.listFiles())) {
                addDirToZip(file, Path.of(innerPth, file.getName()).toString(), zos);
            }
        } else {
            addFileToZip(srcPth, innerPth, zos);
        }
    }
}
