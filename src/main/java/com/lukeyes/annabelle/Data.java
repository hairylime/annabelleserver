package com.lukeyes.annabelle;

import com.lukeyes.annabelle.domain.Favorites;
import com.lukeyes.annabelle.domain.Script;
import com.lukeyes.annabelle.domain.ScriptList;
import com.lukeyes.annabelle.test.TestFavorites;

import javax.swing.*;
import java.io.File;
import java.util.*;

public class Data {

    static Data instance = null;

    public static Data getInstance() {
        if(instance == null) {
            instance = new Data();
        }

        return instance;
    }

    ScriptList masterList;
    Map<String, Script> scripts;
    Favorites favorites;
    String puppetText;


    DefaultListModel<String> historyModel;
    DefaultListModel<String> scriptModel;
    DefaultListModel<String> scriptContentModel;

    public DefaultListModel<String> getScriptModel() {
        return scriptModel;
    }

    public DefaultListModel<String> getScriptContentModel() {
        return scriptContentModel;
    }

    public Map<String, Script> getScripts() {
        return scripts;
    }

    public Favorites getFavorites() {
        return favorites;
    }

    public String getPuppetText() {
        return puppetText;
    }

    public void setPuppetText(String puppetText) {
        this.puppetText = puppetText;
    }

    private Data() {

        scriptModel = new DefaultListModel<>();
        scriptContentModel = new DefaultListModel<>();
        favorites = Favorites.create("Favorites.json");
        historyModel = new DefaultListModel<>();
    }

    private void loadScriptFromMasterList(String parentPath) {
        List<String> titleList2 = masterList.getScripts();

        scripts = new HashMap<>();
        for(String title : titleList2) {
            String absolutePath = String.format("%s\\%s", parentPath, title);
            final Script script = Script.create(absolutePath);
            scripts.put(script.title, script);
        }

        Set<String> titles = scripts.keySet();
        scriptModel.clear();
        titles.forEach(scriptModel::addElement);

        Script currentScript = scripts.get(titles.toArray()[0]);
        scriptContentModel.clear();
        currentScript.lines.forEach(scriptContentModel::addElement);
    }

    public void loadScriptList(File fileToOpen) {
        masterList = ScriptList.open(fileToOpen);
        String parentPath = fileToOpen.getParentFile().getAbsolutePath();
        System.out.println("Parent path: " + parentPath);
        loadScriptFromMasterList(parentPath);
    }
}
