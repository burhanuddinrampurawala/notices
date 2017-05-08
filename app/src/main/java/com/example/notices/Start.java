package com.example.notices;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.zip.Inflater;


public class Start extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public DeleteNotice delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if(FirebaseAuth.getInstance().getCurrentUser() == null)
            navigationView.getMenu().findItem(R.id.session).setTitle("Login");

        else
            navigationView.getMenu().findItem(R.id.session).setTitle("Logout");
        navigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment welcome = new Welcome();
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,welcome).commit();
            }
        });
    }


    @Override
    protected void onResume() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if(FirebaseAuth.getInstance().getCurrentUser() == null){
            navigationView.getMenu().findItem(R.id.session).setTitle("Login");
        }
        else
            navigationView.getMenu().findItem(R.id.session).setTitle("Logout");
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
       else if (count >0) {

            if (getTitle() == "Welcome") {
                finish();
                System.exit(0);
            }
            else {
                Log.i("notice", String.valueOf(getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1)));
                int j = 0;
                String title = "";
                String stack = String.valueOf(getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount() - 1));
                char[] ch = stack.toCharArray();
                for (int i = 0; i < stack.length(); i++) {
                    if (ch[i] == '#') {
                        j = i + 3;
                        break;
                    }
                }
                for (int i = j; i < stack.length() - 1; i++) {
                    title = title + ch[i];
                }
                if (title == "") {

                    Fragment welcome = new Welcome();
                    Log.i("notice","welcome");
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, welcome).commit();
                    setTitle("Welcome");
                } else
                    setTitle(title);
                getSupportFragmentManager().popBackStack();
        }
       }
        else {
            super.onBackPressed();
        }
    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(final MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Bundle bundle = new Bundle();
        Fragment fragmentBranch = new Branch();
        Fragment fragmentlogin = new Login();
        FragmentManager fm = getSupportFragmentManager();
        fm.popBackStack();
        if (id == R.id.fe) {
            bundle.putString("year", "First Year");
            fragmentBranch.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, fragmentBranch).addToBackStack("Welcome").commit();
        } else if (id == R.id.se) {
            bundle.putString("year", "Second Year");
            fragmentBranch.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, fragmentBranch).addToBackStack("Welcome").commit();
        } else if (id == R.id.te) {
            bundle.putString("year", "Third Year");
            fragmentBranch.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, fragmentBranch).addToBackStack("Welcome").commit();
        } else if (id == R.id.be) {
            bundle.putString("year", "Fourth Year");
            fragmentBranch.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, fragmentBranch).addToBackStack("Welcome").commit();

        } else if (id == R.id.session) {
            if (item.getTitle() == "Login") {
//                FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
//                    @Override
//                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                        FirebaseUser user = firebaseAuth.getCurrentUser();
//                        if (user != null) {
//                            item.setTitle("Logout");
//                        }
//                    }
//                };
//                    mAuthListener.onAuthStateChanged(new FirebaseAuth() {
//                        @Nullable
//                        @Override
//                        public FirebaseUser getCurrentUser() {
//                            return super.getCurrentUser();
//                        }
//                    });
                if (FirebaseAuth.getInstance().getCurrentUser() == null)
                    getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment, fragmentlogin).addToBackStack(null).commit();
                else
                    item.setTitle("Logout");

            } else {
                Log.i("notice", FirebaseAuth.getInstance().getCurrentUser().toString());
                FirebaseAuth.getInstance().signOut();
                item.setTitle("Login");
            }
        }


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;


    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.contextmenu,menu);
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int i = info.position;
        switch (item.getItemId()) {
            case R.id.edit:
                Fragment fragmentadd = new Add();
                Bundle bundle = new Bundle();
                bundle.putString("edit","edit");
                bundle.putInt("id",i);
                fragmentadd.setArguments(bundle);
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                fm.popBackStack();
                ft.remove(fm.findFragmentById(R.id.mainFragment)).add(R.id.mainFragment,fragmentadd);
                ft.commit();
                return true;
            case R.id.delete:
                //if(FirebaseAuth.getInstance().getCurrentUser() != null){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        delete = new DeleteNotice(i,Start.this);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.setTitle("Delete Notice");
                dialog.setMessage("Are you sure you want to delete this notice?");
                dialog.show();
                return true;
            //}

//                else{
//                    Toast.makeText(getApplicationContext(),"yow will have to Sign in first",Toast.LENGTH_SHORT);
//                    return false;
//                }

            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionmenu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (FirebaseAuth.getInstance().getCurrentUser() == null){
            menu.getItem(0).setEnabled(false);
            menu.getItem(0).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.add) {
            Fragment fragmentadd = new Add();
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFragment,fragmentadd).addToBackStack(null).commit();
        }

        return true;
    }
}
