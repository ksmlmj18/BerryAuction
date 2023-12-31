package com.example.main_;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class Title extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);   //theme로 지정했다면 삭제한다.

        ImageView berry = (ImageView) findViewById(R.id.iv_berry2);
        Glide.with(this).load(R.raw.berry2).into(berry);

        moveMain(2);   //2초 후 main activity 로 넘어감
    }

    private void moveMain(int sec) {
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                //new Intent(현재 context, 이동할 activity)
                Intent intent = new Intent(getApplicationContext(), Main.class);

                startActivity(intent);   //intent 에 명시된 액티비티로 이동

                finish();   //현재 액티비티 종료
            }
        }, 2000 * sec); // sec초 정도 딜레이를 준 후 시작
    }
}