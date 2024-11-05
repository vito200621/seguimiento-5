package org.example.demo4.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.example.demo4.model.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class MemberController {
    private static MemberController instance;
    public ObservableList<Member> memberList;
    public ArrayList<Member> members;
    private File data;
    private File listJson;


    public MemberController(){
        members = new ArrayList<>();
        memberList = FXCollections.observableArrayList(members);
        File projectDir = new File(System.getProperty("user.dir"));

        // Win -> C:\\Users\\user\\Documents\\
        // Unix -> /home/user/Documents/
        // Linux -> /home/user/Documents/

        // win -> this.data = new File(projectDir+"\\src\\main\\data");
        this.data = new File(projectDir+"/src/main/data");
        this.listJson = new File(data.getAbsoluteFile()+"/members.json");
    }
    public static MemberController getInstance(){
        if(instance == null){
            instance = new MemberController();
        }
        return instance;
    }

    public void createResources() throws IOException {
        if(!data.exists()){
            data.mkdir();
            if(!listJson.exists()){
                listJson.createNewFile();
            }
        }
    }

    public void createMember(String name, String type, double length, double calories){
        Member Member = new Member(name, type, length, calories);
        members.add(Member);
        memberList.add(Member);
    }

    public void clearList(){
        members.removeAll(members);
    }

    public ArrayList<Member> getMembers(){
        return members;
    }
    public ObservableList<Member> getMemberList() {
        return memberList;
    }

    public String getData() {
        StringBuilder activities = new StringBuilder();
        for (Member member : members) {
            activities.append(member.toString()).append("\n");
        }
        return activities.toString();
    }

    public void save(){
        // el objeto que nos ayuda con al serialización
        Gson gson = new Gson();

        // Formateamos la información (arraylist -> JSON)
        String dataJson = gson.toJson(members);
        // System.out.println(dataJson);

        try {
            // inicializar los recursos
            createResources();

            // Enlazar la información con el archivo
            FileOutputStream fos = new FileOutputStream(this.listJson);
            // Escritor de la información
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos));

            // Escribir la información
            writer.write(dataJson);
            // limpiar el buffer
            writer.flush();
            // cerrar el buffer
            writer.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void load(){
        Gson gson = new Gson();

        try {
            // enlazador de la información
            FileInputStream fis = new FileInputStream(this.listJson);

            BufferedReader reader = new BufferedReader(new InputStreamReader(fis));

            String line = "";
            String data = "";

            while ( (line = reader.readLine()) != null ){
                data += line;
            }
            reader.close();

            // https://stackoverflow.com/questions/20773850/gson-typetoken-with-dynamic-arraylist-item-type
            Type listType = new TypeToken<ArrayList<Member>>(){}.getType();
            members = gson.fromJson(data, listType);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

