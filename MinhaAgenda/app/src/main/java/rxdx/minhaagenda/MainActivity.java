package rxdx.minhaagenda;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    SwipeRefreshLayout swipeRefreshLayout;
    Button buttonOk;
    EditText myEditText;

    List<User> users = new ArrayList<>();
    private MyAdapter adapter;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.204.227.14:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    UsersService service = retrofit.create(UsersService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.myListView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                User user = users.get(position);
                service.deleteUser(user.getId()).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Response<User> response, Retrofit retrofit) {
                        Toast.makeText(MainActivity.this, "Sucesso", Toast.LENGTH_SHORT).show();

                        getUsers();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(MainActivity.this, "Erro", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        adapter = new MyAdapter(this, users);
        listView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getUsers();
            }
        });

        buttonOk = (Button) findViewById(R.id.buttonOk);
        myEditText = (EditText) findViewById(R.id.myEditText);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = myEditText.getText().toString();
                User user = new User(name+"@email.com", name);

                service.createUser(user).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Response<User> response, Retrofit retrofit) {
                        Toast.makeText(MainActivity.this, "Sucesso", Toast.LENGTH_SHORT).show();
                        getUsers();
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(MainActivity.this, "Falha", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

        getUsers();
    }

    private void getUsers() {
        service.getUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Response<List<User>> response, Retrofit retrofit) {
                Toast.makeText(MainActivity.this, "Sucesso", Toast.LENGTH_SHORT).show();

                MainActivity.this.users = response.body();
                listView.setAdapter(new MyAdapter(MainActivity.this, users));

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(MainActivity.this, "Falha", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }
}
