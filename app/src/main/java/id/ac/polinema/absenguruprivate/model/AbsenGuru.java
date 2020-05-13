package id.ac.polinema.absenguruprivate.model;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

import id.ac.polinema.absenguruprivate.R;

public class AbsenGuru extends AbstractItem<AbsenGuru, AbsenGuru.ViewHolder> {
    private String username;
    private String password;
    private String jam_login;
    private String jam_logout;
    private String tanggal;

    public AbsenGuru(String username, String password, String jam_login, String jam_logout, String tanggal) {
        this.username = username;
        this.password = password;
        this.jam_login = jam_login;
        this.jam_logout = jam_logout;
        this.tanggal = tanggal;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJam_login() {
        return jam_login;
    }

    public void setJam_login(String jam_login) {
        this.jam_login = jam_login;
    }

    public String getJam_logout() {
        return jam_logout;
    }

    public void setJam_logout(String jam_logout) {
        this.jam_logout = jam_logout;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    @NonNull
    @Override
    public AbsenGuru.ViewHolder getViewHolder(View v) {
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


    public class ViewHolder extends FastAdapter.ViewHolder<AbsenGuru> {
        private TextView jam_login, jam_logout, tanggal;

        public ViewHolder(View itemView) {
            super(itemView);
            jam_login = itemView.findViewById(R.id.txt_jam_login);
            jam_logout = itemView.findViewById(R.id.txt_jam_logout);
            tanggal = itemView.findViewById(R.id.txt_tanggal);
        }

        @Override
        public void bindView(AbsenGuru item, List<Object> payloads) {
            jam_login.setText(item.jam_login);
            jam_logout.setText(item.jam_logout);
            tanggal.setText(item.tanggal);
        }

        @Override
        public void unbindView(AbsenGuru item) {
            jam_login.setText(null);
            jam_logout.setText(null);
            tanggal.setText(null);
        }
    }
}
