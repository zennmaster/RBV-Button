package com.zennmaster.rebootButton;


import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;




public class rebootButton extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    
        //  Check for the Voodoo directory:
        String rootpath = Environment.getExternalStorageDirectory().toString();
        String vpath = (rootpath+"/voodoo/");
        File vpfile = new File (vpath);
        final boolean vpath_exists = vpfile.exists();
        
        //check for diable_lagfix file:
        String vfile = (vpath + "disable_lagfix");
        File vffile = new File (vfile);
        final boolean vfile_exists = vffile.exists();
        
        final ToggleButton Voodootoggle = (ToggleButton) findViewById(R.id.ToggleButton01);
    
        if (vpath_exists){
        	
        	if (vfile_exists){        		
        		Voodootoggle.setChecked(false);
        		Toast.makeText(rebootButton.this, "VooDoo Exists, disabled", Toast.LENGTH_SHORT).show();
        	}
        	else {
        		Voodootoggle.setChecked(true);
        		Toast.makeText(rebootButton.this, "VooDoo exists, enabled", Toast.LENGTH_SHORT).show();
        	}
        }
        else{
        	// File vpath_new = new File(vpath); // Creating Voodoo folder.  This should be removed
        	// vpath_new.mkdirs();				  // before release.
        	Toast.makeText(rebootButton.this, "VooDoo Not Found!", Toast.LENGTH_SHORT).show();        	
        }
       
        
        // final ToggleButton Voodootoggle = (ToggleButton) findViewById(R.id.ToggleButton01);
        Voodootoggle.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	// Perform action on clicks
            	
            if (vpath_exists){  //if Voodoo is installed, we can do some stuff, otherwise not.
            		
            	
                if (Voodootoggle.isChecked()) { //VooDoo is ON, so we don't want a file...
                	File file = new File("/sdcard/voodoo/disable_lagfix");
                	boolean deleted = file.delete();
                	if (deleted){
                    Toast.makeText(rebootButton.this, "VooDoo On!", Toast.LENGTH_SHORT).show();
                	}
                	
                	else {
                		Toast.makeText(rebootButton.this, "VooDoo BooBoo!", Toast.LENGTH_SHORT).show(); //Problem!
                	}
                	
                }
                else {
                	generateVfile("disable_lagfix");
                	Toast.makeText (rebootButton.this, "VooDoo Off!", Toast.LENGTH_SHORT).show();
                	}
               
                } 
            
            else {
            	Toast.makeText (rebootButton.this, "VooDoo Not Installed!", Toast.LENGTH_SHORT).show();
            	Voodootoggle.setChecked(false);
            }
            }
        });
        
        final Button Rebootnowbutton = (Button) findViewById(R.id.RBNowButton); // "Reboot" button action.
        Rebootnowbutton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		runRootCommand("reboot");
        		
        	}
        });
        
        final Button RebootRcvbutton = (Button) findViewById(R.id.RBRcvButton); // "Reboot Recovery" button action.
        RebootRcvbutton.setOnClickListener(new OnClickListener() {
        	public void onClick(View v) {
        		runRootCommand("reboot recovery");
        		
            }
        });
        
       
    
 
    }

   

    /* 
	 * Creating an options menu.  Right now just credits, but we'll see...
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.icon_menu, menu);
		return true;
	}



	/* 
	 * Right now just showing credits.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Toast.makeText(rebootButton.this, "Thanks for using Reboot Button with VooDoo Toggle, By Zennmaster!", Toast.LENGTH_LONG).show();
		return super.onOptionsItemSelected(item);
	}

   
    public static boolean runRootCommand(String command) {
        Process process = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command+"\n");
            os.writeBytes("exit\n");
            os.flush();
            process.waitFor();
        } catch (Exception e) {
            Log.d("*** DEBUG ***", "Error - "+e.getMessage());
            return false;
        }
        finally {
            try {
                if (os != null) {
                    os.close();
                }
                process.destroy();
            } catch (Exception e) {
               
            }
        }
        return true;
    }

	private static void generateVfile(String vFilename)
    {
    	try  // Generates the disable_lagfix file.
    	{
    	
    	String root = Environment.getExternalStorageDirectory().toString();
    	String Voodoopath = (root+"/voodoo/");
    	File Voodoofile = new File (Voodoopath, vFilename);
    	FileWriter writer = new FileWriter(Voodoofile);
    	writer.close();
    	}
    	catch(IOException e)
    	{
    		e.printStackTrace();
    	}
    }
}



        
    
    	
    
