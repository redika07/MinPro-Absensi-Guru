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
import id.ac.polinema.absenguruprivate.model.GuruItem;
import id.ac.polinema.absenguruprivate.rest.ApiClient;
import id.ac.polinema.absenguruprivate.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentDataGuru extends Fragment {

    public FragmentDataGuru() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_data_guru, container, false);

        Session session = new Session(getActivity());

        final RecyclerView guruView = root.findViewById(R.id.rv_guru);
        final ItemAdapter itemAdapter = new ItemAdapter<>();
        final FastAdapter fastAdapter = FastAdapter.with(itemAdapter);

        final List guru = new ArrayList<>();

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<List<GuruItem>> call = apiInterface.getGuru();

        call.enqueue(new Callback<List<GuruItem>>() {
            @Override
            public void onResponse(Call<List<GuruItem>> call, Response<List<GuruItem>> response) {
                List<GuruItem> guruItems = response.body();
                if (response.isSuccessful()) {
                    for (GuruItem item : guruItems) {
                        guru.add(new GuruItem(item.getId_guru(), item.getNama(), item.getAlamat(), item.getJenis_kelamin(),
                                item.getNo_telp(), item.getFoto(), item.getUsername(), item.getPassword()));
                    }

                    itemAdapter.add(guru);
                    guruView.setAdapter(fastAdapter);

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    guruView.setLayoutManager(layoutManager);
                } else {
                    Toast.makeText(getActivity(), "Gagal menampilkan data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<GuruItem>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}
