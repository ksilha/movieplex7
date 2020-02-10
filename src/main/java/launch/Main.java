/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package launch;

import java.io.File;
import java.io.IOException;
import org.glassfish.embeddable.Deployer;
import org.glassfish.embeddable.GlassFish;
import org.glassfish.embeddable.GlassFishException;
import org.glassfish.embeddable.GlassFishProperties;
import org.glassfish.embeddable.GlassFishRuntime;
import org.glassfish.embeddable.archive.ScatteredArchive;

/**
 *
 * @author ksilh
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws GlassFishException, IOException {
        String port = System.getenv("PORT");
        port = port != null ? port : "8080";
        GlassFishProperties gfProps = new GlassFishProperties();
        gfProps.setPort("http-listener", Integer.parseInt(port));

        GlassFish glassfish = GlassFishRuntime.bootstrap().newGlassFish(gfProps);
        glassfish.start();

        File webRoot = new File("src/main/webapp");
        File classRoot = new File("target", "classes");

        Deployer deployer = glassfish.getDeployer();
        ScatteredArchive archive = new ScatteredArchive("/", ScatteredArchive.Type.WAR, webRoot);
        archive.addClassPath(classRoot);
        deployer.deploy(archive.toURI());
    }

}
