package id.ac.polinema.absenguruprivate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mikepenz.fastadapter.FastAdapter;
import com.mikepenz.fastadapter.adapters.ItemAdapter;

import java.util.ArrayList;
import java.util.List;

import id.ac.polinema.absenguruprivate.helper.Session;
import id.ac.polinema.absenguruprivate.model.SiswaItem;
import id.ac.polinema.absenguruprivate.rest.ApiClient;
import id.ac.polinema.absenguruprivate.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDataSiswa extends Fragment {

    public FragmentDataSiswa() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_data_siswa, container, false);

        Session session = new Session(getActivity());

        final RecyclerView siswaView = root.findViewById(R.id.rv_siswa);
        final ItemAdapter itemAdapter = new ItemAdapter<>();
        final FastAdapter fastAdapter = FastAdapter.with(itemAdapter);

        final List siswa = new ArrayList<>();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<SiswaItem>> call = apiInterface.getSiswa();

        call.enqueue(new Callback<List<SiswaItem>>() {
            @Override
            public void onResponse(Call<List<SiswaItem>> call, Response<List<SiswaItem>> response) {
                List<SiswaItem> SiswaItems = response.body();
                if (response.isSuccessful()) {
                    for (SiswaItem item : SiswaItems) {
                        siswa.add(new SiswaItem(item.getNim(), item.getNama(), item.getAlamat(),
                                item.getJenis_kelamin(), item.getTanggal_lahir(), item.getKelas()));
                    }

                    itemAdapter.add(siswa);
                    siswaView.setAdapter(fastAdapter);

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    siswaView.setLayoutManager(layoutManager);
                } else {
                    Toast.makeText(getActivity(), "Gagal menampilkan data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<SiswaItem>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}
