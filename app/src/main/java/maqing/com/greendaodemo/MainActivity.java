package maqing.com.greendaodemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import maqing.com.greendaodemo.entity.User;
import maqing.com.greendaodemo.gen.DaoMaster;
import maqing.com.greendaodemo.gen.DaoSession;
import maqing.com.greendaodemo.gen.UserDao;

import android.app.Activity;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {
    Button insertBtn, queryBtn;
    DaoMaster.DevOpenHelper openHelper;
    DaoMaster master;
    DaoSession session;
    UserDao userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    /**
     * 初始化
     */
    public void init() {
        initData();
        initView();
    }

    public void initView() {
        insertBtn = (Button) findViewById(R.id.insert_btn);
        queryBtn = (Button) findViewById(R.id.query_btn);
        insertBtn.setOnClickListener(this);
        queryBtn.setOnClickListener(this);
    }


    public void initData() {
        openHelper = new DaoMaster.DevOpenHelper(this, "testdb");
        master = new DaoMaster(openHelper.getWritableDb());
        session = master.newSession();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.insert_btn:
                userDAO = session.getUserDao();
                User user = new User();
                user.setId(3L);
                user.setName("张三");
                long id = userDAO.insert(user);
                Log.i("MainActivity", id + "");
                break;
            case R.id.query_btn:
                userDAO = session.getUserDao();
                List<User> users = userDAO.queryBuilder().build().list();
                String result = "";
                for (User us : users) {
                    result += us.toString();
                }
                Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
