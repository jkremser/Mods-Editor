
package cz.mzk.EditMods;

import java.util.Arrays;

import cz.fi.muni.xkremser.editor.client.mods.ModsCollectionClient;
import cz.fi.muni.xkremser.editor.client.view.ModsTab;

import cz.fi.muni.xkremser.editor.server.mods.ModsCollection;
import cz.fi.muni.xkremser.editor.server.util.BiblioModsUtils;

/**
 * Hello world!
 */
public class Demo {

    //    private FedoraAccess fedoraAccess;
    //
    //    @Inject
    //    @Named("securedFedoraAccess")
    //    public void setFedoraAccess(FedoraAccess fedoraAccess) {
    //        this.fedoraAccess = fedoraAccess;
    //    }
    //    or via constructor injection

    public static void main(String[] args) {
        // 1) modsClient -> xml
        // do the following on the client side
        ModsTab tab = new ModsTab(1);
        // or ModsTab tab = new ModsTab(1, modsClient); in case we have an existing mods for editing
        // add tab to some layouts and let the user to use them, then call
        ModsCollectionClient modsClient = new ModsCollectionClient();
        modsClient.setMods(Arrays.asList(tab.getMods()));

        // do the following on the server side
        ModsCollection mods = BiblioModsUtils.toMods(modsClient);
        String xml = BiblioModsUtils.toXML(mods);
        // String xml is the final product

        // 2) xml -> editable ModsTab 
        // do the following on the server side
        //        org.w3c.dom.Document modsDocument = fedoraAccess.getBiblioMods("uuid");
        //        mods = BiblioModsUtils.getModsCollection(modsDocument);
        modsClient = BiblioModsUtils.toModsClient(mods);
        // do the following on the client side
        tab = new ModsTab(1, modsClient);
        // ModsTab tab is the final product

    }
}
