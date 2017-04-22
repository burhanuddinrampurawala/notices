package com.example.notices;

import android.app.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.*;




public class NoticeList extends Activity {

    private static final String TAG = login.class.getSimpleName();
    private SQLiteHandler db;
    private SessionManager session;
    private ArrayAdapter<String> noticeadapter;
    private ListView noticelist;
    private DeleteNotice delete;
    private Handler mHandler;
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);
        db = new SQLiteHandler(getApplicationContext());
        session = new SessionManager(getApplicationContext());
        getnotice();

    }


    private void getnotice()
    {

        DatabaseReference mPostReference = FirebaseDatabase.getInstance().getReference();
        mPostReference.keepSynced(true);
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                if(dataSnapshot != null){
                    Iterable<DataSnapshot> a = dataSnapshot.getChildren();
                    String[] head = new String[(int) dataSnapshot.getChildrenCount()];
                    String[] title = new String[(int) dataSnapshot.getChildrenCount()];
                    String[] description = new String[(int) dataSnapshot.getChildrenCount()];
                    int i = 0;
                    for(DataSnapshot m : a){
                        head[i] = m.child("head").getValue(String.class);
                        title[i] = m.child("title").getValue(String.class);
                        description[i] = m.child("description").getValue(String.class);
                        i++;
                    }

                    adapter(head,title,description);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }

        };
        mPostReference.addValueEventListener(postListener);
    }
    private void adapter(String [] head, final String [] title, final String [] description){
        noticeadapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1,
                head);
        noticelist = (ListView) findViewById(R.id.noticeListView);
        noticelist.setAdapter(noticeadapter);
        noticelist.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent i = new Intent(getApplicationContext(),DisplayDescription.class);
                        i.putExtra("description", description[position]);
                        i.putExtra("title", title[position]);
                        startActivity(i);
                    }
                }
        );
        registerForContextMenu(noticelist);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        //if (session.isadmin()){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.contextmenu,menu);//}
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final int i = info.position;
        switch (item.getItemId()) {
            case R.id.edit:
                Intent intent = new Intent(getApplicationContext(),AddNotice.class);
                intent.putExtra("id", i);
                startActivity(intent);
                return true;
            case R.id.delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        noticeadapter.remove(noticeadapter.getItem(i));
//                        noticeadapter.notifyDataSetChanged();
                        delete = new DeleteNotice(i,NoticeList.this);
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

//        if (!session.isadmin()){
//            menu.getItem(1).setEnabled(false);
//            menu.getItem(1).setVisible(false);
//        }
        return super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {


            if (session.isLoggedIn()){
                db.deleteUsers();
            }
            session.setLogin(false);
            session.setAdmin(false);
            // Launching the login activity
            Intent intent = new Intent(getApplicationContext(), login.class);
            startActivity(intent);
            finish();
        }
        else if (id == R.id.add) {
            Intent intent = new Intent(getApplicationContext(), AddNotice.class);
            startActivity(intent);
            finish();
        }

        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            this.finish();
            System.exit(0);
            return true;
        }
        return false;
    }

}