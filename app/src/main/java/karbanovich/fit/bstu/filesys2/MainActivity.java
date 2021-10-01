package karbanovich.fit.bstu.filesys2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.Buffer;

public class MainActivity extends AppCompatActivity {

    private EditText surname;
    private EditText name;
    private final static String FILE_NAME = "Base_Lab.txt";
    private TextView fileContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        surname = (EditText) findViewById(R.id.edtTxtSurname);
        name = (EditText) findViewById(R.id.edtTxtName);
        fileContents = (TextView) findViewById(R.id.txtFileContents);

        if(!existBase(FILE_NAME))
            if(createFile(FILE_NAME))
                fileCreationDialog(FILE_NAME);

        output(FILE_NAME);
    }

    public void input(View view) {
        if(!existBase(FILE_NAME)) {
            createFile(FILE_NAME);
            fileCreationDialog(FILE_NAME);
        }
        writeLine(FILE_NAME, surname.getText().toString() + "; " + name.getText().toString() + ";\r\n");
        output(FILE_NAME);
    }

    private boolean existBase(String fileName) {
        File f = new File(super.getFilesDir(), fileName);

        if(f.exists())
            Log.d("Log","Файл " + fileName + " существует");
        else
            Log.d("Log","Файл " + fileName + " не найден");

        return f.exists();
    }

    private boolean createFile(String fileName) {
        File f = new File(super.getFilesDir(), fileName);

        try {
            f.createNewFile();
            Log.d("Log","Файл " + fileName + " создан");
            return true;
        } catch(IOException e ) {
            Log.d("Log","Файл " + fileName + " не создан");
            return false;
        }
    }

    private void fileCreationDialog(String fileName) {
        AlertDialog.Builder b = new AlertDialog.Builder(this);

        b.setTitle("Создан файл " + fileName)
                .setPositiveButton("ОК", null)
                .create()
                .show();
    }

    private void writeLine(String fileName, String s) {
        File f = new File(super.getFilesDir(), fileName);

        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
            bw.write(s);
            bw.close();
            Log.d("Log", "Данные записаны в файл " + fileName);
        } catch (IOException e) {
            Log.d("Log", "Файл " + fileName + " не открыт");
        }
    }

    private void output(String fileName) {
        File f = new File(super.getFilesDir(), fileName);
        String line;
        StringBuilder sb;

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            sb = new StringBuilder();

            while((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\r\n");
            }
            br.close();

            fileContents.setText(sb);
        } catch (IOException e) {
            Log.d("Log", "Ошибка чтения из файла " + fileName);
        }
    }
}