package id.ac.polinema.absenguruprivate.model;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import id.ac.polinema.absenguruprivate.MapsActivity;
import id.ac.polinema.absenguruprivate.R;

public class AbsenItem extends AbstractItem<AbsenItem, AbsenItem.ViewHolder> {
    private String username;
    private String password;
    private String jam_login;
    private String jam_logout;
    private String tanggal;
    private double lokasi_latitude;
    private double lokasi_longitude;
    private String nim_siswa;
    private String nama;
    private String alamat;

    public AbsenItem(String username, String password, String jam_login, String jam_logout, String tanggal, double lokasi_latitude, double lokasi_longitude, String nim_siswa, String nama, String alamat) {
        this.username = username;
        this.password = password;
        this.jam_login = jam_login;
        this.jam_logout = jam_logout;
        this.tanggal = tanggal;
        this.lokasi_latitude = lokasi_latitude;
        this.lokasi_longitude = lokasi_longitude;
        this.nim_siswa = nim_siswa;
        this.nama = nama;
        this.alamat = alamat;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getJam_login() {
        return jam_login;
    }

    public String getJam_logout() {
        return jam_logout;
    }

    public String getTanggal() {
        return tanggal;
    }

    public double getLokasi_latitude() {
        return lokasi_latitude;
    }

    public double getLokasi_longitude() {
        return lokasi_longitude;
    }

    public String getNim_siswa() {
        return nim_siswa;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    @NonNull
    @Override
    public AbsenItem.ViewHolder getViewHolder(View v) {
        return new ViewHolder(v);
    }

    @Override
    public int getType() {
        return R.id.rv_absen;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_absen;
    }


    public class ViewHolder extends FastAdapter.ViewHolder<AbsenItem> {
        private TextView jam_login, jam_logout, tanggal, nama, alamat;

        public ViewHolder(View itemView) {
            super(itemView);
            jam_login = itemView.findViewById(R.id.txt_jam_login);
            jam_logout = itemView.findViewById(R.id.txt_jam_logout);
            tanggal = itemView.findViewById(R.id.txt_tanggal);
            nama = itemView.findViewById(R.id.txt_siswa_diajar);
            alamat = itemView.findViewById(R.id.txt_alamat_siswa);
        }

        @Override
        public void bindView(final AbsenItem item, List<Object> payloads) {
            jam_login.setText(item.jam_login);
            jam_logout.setText(item.jam_logout);
            tanggal.setText(item.tanggal);
            nama.setText(item.nama);
            alamat.setText(item.alamat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = itemView.getContext();
                    Intent intent = new Intent(context, MapsActivity.class);
                    intent.putExtra("latitude", item.lokasi_latitude);
                    intent.putExtra("longitude", item.lokasi_longitude);
                    context.startActivity(intent);
                }
            });

        }

        @Override
        public void unbindView(AbsenItem item) {
            jam_login.setText(null);
            jam_logout.setText(null);
            tanggal.setText(null);
            nama.setText(null);
            alamat.setText(null);
        }
    }
}
