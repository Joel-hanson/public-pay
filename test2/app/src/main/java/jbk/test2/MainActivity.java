package jbk.test2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      //  Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
     //   setSupportActionBar(toolbar);

       // getSupportActionBar().hide();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void create(View v){
        Toast.makeText(MainActivity.this, "asdasdasdasdas", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void sign_in(View v){
        new IntentIntegrator(this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == IntentIntegrator.REQUEST_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                IntentResult intentResult =
                        IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if (intentResult != null) {

                    String contents = intentResult.getContents();
                    String format = intentResult.getFormatName();

                    //  TextView uno = (TextView) findViewById(R.id.textView1);
                    //  uno.setText(contents);
                    //Toast.makeText(this, "Numero: " + contents, Toast.LENGTH_LONG).show();
                    Log.d("anik",  contents);

                    try {
                        XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
                        XmlPullParser myparser = xmlFactoryObject.newPullParser();
                        InputStream stream = new ByteArrayInputStream(contents.getBytes(StandardCharsets.UTF_8));
                        myparser.setInput(stream,null);
                        int event = myparser.getEventType();
                        while (event != XmlPullParser.END_DOCUMENT)
                        {
                            String name=myparser.getName();
                            switch (event){
                                case XmlPullParser.START_TAG:
                                    break;

                                case XmlPullParser.END_TAG:
                                    if(name.equals("PrintLetterBarcodeData")){
                                       // temperature = myparser.getAttributeValue(null,"value");
                                            String uid = myparser.getAttributeValue(null,"uid");
                                            String namee = myparser.getAttributeValue(null,"name");

                                        String uid_acc = ((EditText)findViewById(R.id.editText2)).getText().toString()
                                                ,name_acc=((EditText)findViewById(R.id.editText1)).getText().toString();

                                        if( uid.compareTo(uid_acc)==0 && namee.compareTo(name_acc)==0)
                                        Toast.makeText(MainActivity.this,"Welcome "+ myparser.getAttributeValue(null, "name"), Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(MainActivity.this,"ID/Name does not match adhar card", Toast.LENGTH_SHORT).show();

                                        Log.d("anik", myparser.getAttributeValue(null,"name"));
                                    }
                                    break;
                            }
                            event = myparser.next();
                        }
                    } catch(Exception e){}





                } else {
                    Log.e("SEARCH_EAN", "IntentResult je NULL!");
                }
                // Do something with the contact here (bigger example below)
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
