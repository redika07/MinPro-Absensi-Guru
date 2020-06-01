package id.ac.polinema.absenguruprivate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.ac.polinema.absenguruprivate.model.AbsenItem;
import id.ac.polinema.absenguruprivate.model.GuruItem;
import id.ac.polinema.absenguruprivate.model.SiswaItem;
import id.ac.polinema.absenguruprivate.rest.ApiClient;
import id.ac.polinema.absenguruprivate.rest.ApiInterface;
import id.ac.polinema.absenguruprivate.helper.Session;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuruActivity extends AppCompatActivity {
    private ImageView profil;
    private TextView id_guru, nama, alamat, jenis_kelamin, no_telp, username, password;
    private Session session;
    private CardView guru;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guru);

        session = new Session(getApplicationContext());

        guru = findViewById(R.id.item_profil);
        guru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailAbsenActivity.class);
                intent.putExtra("username", session.getUsername());
                startActivity(intent);
            }
        });

        final RecyclerView siswaView = findViewById(R.id.rv_siswa);
        final ItemAdapter itemAdapter = new ItemAdapter<>();
        final FastAdapter fastAdapter = FastAdapter.with(itemAdapter);

        final List siswa = new ArrayList<>();

        profil = findViewById(R.id.foto_profil);
        id_guru = findViewById(R.id.id_guru);
        nama = findViewById(R.id.nama_guru);
        alamat = findViewById(R.id.alamat_guru);
        jenis_kelamin = findViewById(R.id.jenis_kelamin_guru);
        no_telp = findViewById(R.id.telp_guru);
        username = findViewById(R.id.username_guru);
        password = findViewById(R.id.password_guru);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<GuruItem>> call = apiInterface.getGuruByUsername(session.getUsername());

        call.enqueue(new Callback<List<GuruItem>>() {
            @Override
            public void onResponse(Call<List<GuruItem>> call, Response<List<GuruItem>> response) {
                if (response.isSuccessful()) {
                    GuruItem item = response.body().get(0);

                    Picasso.get().load(item.getFoto()).into(profil);
                    id_guru.setText(item.getId_guru());
                    nama.setText(item.getNama());
                    alamat.setText(item.getAlamat());
                    jenis_kelamin.setText(item.getJenis_kelamin());
                    no_telp.setText(item.getNo_telp());
                    username.setText(item.getUsername());
                    password.setText(item.getPassword());
                } else {
                    Toast.makeText(getApplicationContext(), "Gagal menampilkan data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GuruItem>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        Call<List<SiswaItem>> call1 = apiInterface.getSiswa();

        call1.enqueue(new Callback<List<SiswaItem>>() {
            @Override
            public void onResponse(Call<List<SiswaItem>> call, Response<List<SiswaItem>> response) {
                if (response.isSuccessful()) {
                    List<SiswaItem> siswaItems = response.body();

                    for (SiswaItem item : siswaItems) {
                        siswa.add(new SiswaItem(item.getNim(), item.getNama(), item.getAlamat(), item.getJenis_kelamin(),
                                item.getTanggal_lahir(), item.getKelas()));
                    }

                    itemAdapter.add(siswa);
                    siswaView.setAdapter(fastAdapter);

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    siswaView.setLayoutManager(layoutManager);
                } else {
                    Toast.makeText(getApplicationContext(), "Data gagal ditampilkan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SiswaItem>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        if (ContextCompat.checkSelfPermission(GuruActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(GuruActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(GuruActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else{
                ActivityCompat.requestPermissions(GuruActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }

        LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertMessageNoGps();
        }
    }

    private void AlertMessageNoGps() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1: {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(GuruActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
