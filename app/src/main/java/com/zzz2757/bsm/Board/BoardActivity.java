package com.zzz2757.bsm.Board;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zzz2757.bsm.R;

public class BoardActivity extends AppCompatActivity {
    private String boardType;

    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private BoardFrag boardFrag;
    private BoardFrag anonymousFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()) {
                    case R.id.action_board:
                        setFrag(0);
                        break;
                    case R.id.action_anonymous:
                        setFrag(1);
                        break;
                    case R.id.action_post_write:
                        setFrag(2);
                        break;

                }
                return true;
            }
        });
        Bundle boardBundle = new Bundle(1);
        boardFrag = new BoardFrag();
        boardBundle.putString("boardType", "board");
        boardFrag.setArguments(boardBundle);

        Bundle anonymousBundle = new Bundle(1);
        anonymousFrag = new BoardFrag();
        anonymousBundle.putString("boardType", "anonymous");
        anonymousFrag.setArguments(anonymousBundle);
        setFrag(0);
    }

    private void setFrag(int n) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();
        switch(n){
            case 0:
                boardType = "board";
                fragmentTransaction.replace(R.id.Main_Frame, boardFrag);
                fragmentTransaction.commit();
                break;
            case 1:
                boardType = "anonymous";
                fragmentTransaction.replace(R.id.Main_Frame, anonymousFrag);
                fragmentTransaction.commit();
                break;
            case 2:
                Intent intent = new Intent(this, PostWriteActivity.class);
                intent.putExtra("boardType", boardType);
                startActivity(intent);
        }
    }
}
