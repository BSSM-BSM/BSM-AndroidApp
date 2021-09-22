package com.zzz2757.bsm.Board;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.zzz2757.bsm.R;

public class BoardActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private BoardFrag boardFrag;
    private BoardFrag blogFrag;

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
                    case R.id.action_blog:
                        setFrag(1);
                        break;
                }
                return true;
            }
        });
        Bundle boardBundle = new Bundle(1);
        boardFrag = new BoardFrag();
        boardBundle.putString("boardType", "board");
        boardFrag.setArguments(boardBundle);

        Bundle blogBundle = new Bundle(1);
        blogFrag = new BoardFrag();
        blogBundle.putString("boardType", "blog");
        blogFrag.setArguments(blogBundle);
        setFrag(0);
    }

    private void setFrag(int n) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction= fragmentManager.beginTransaction();
        switch(n){
            case 0:
                fragmentTransaction.replace(R.id.Main_Frame, boardFrag);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentTransaction.replace(R.id.Main_Frame, blogFrag);
                fragmentTransaction.commit();
                break;
        }
    }
}
