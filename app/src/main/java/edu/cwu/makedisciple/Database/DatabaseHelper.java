package edu.cwu.makedisciple.Database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {

    //Android's default system path for app's database
    private static String DB_PATH = "/data/data/edu.cwu.makedisciple.Database/databases/";
    private static String DB_NAME  ="bookContent";
    private SQLiteDatabase myDatabase;
    private final Context myContext;
    
    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;

    }


    public void createDatabase() throws IOException{
        boolean dbExist = checkDataBase();
        if(dbExist){
            //do nothing
        }else{
            try{
                copyDataBase();
            }catch (IOException e){
                throw new Error("Error copying database");
            }
        }
    }



    private boolean checkDataBase() {
        SQLiteDatabase checkDb = null;
        try{
            String myPath = DB_PATH+DB_NAME;
            checkDb = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);
        }catch (SQLException e){
            //database doesn't exist
        }
        if (checkDb != null){
            checkDb.close();
        }
        return checkDb != null ? true:false;
    }

    //this will copy the database over from the assets folder
    private void copyDataBase()throws IOException {
        //opening local db
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        String outFileName =DB_PATH+DB_NAME;

        OutputStream myOutput = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer,0,length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void OpenDataBase()throws IOException{
        String myPath = DB_PATH+DB_NAME;
        myDatabase = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READONLY);
    }
    @Override
    public synchronized void close(){
        if (myDatabase !=null){
            myDatabase.close();
        }
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
