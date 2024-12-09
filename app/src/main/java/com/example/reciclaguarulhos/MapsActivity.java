package com.example.reciclaguarulhos;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private TextView txtLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        txtLocation = findViewById(R.id.txtLocation);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button backButton = findViewById(R.id.btnVoltar);

        backButton.setOnClickListener(v -> {
            finish();
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setCompassEnabled(false);


        mMap.setPadding(100, 0, 0, 1950);

        adicionarMarker(mMap, new LatLng(-23.55052, -46.633308), "São Paulo", "Centro de SP");
        adicionarMarker(mMap, new LatLng(-23.468110, -46.532465), "Guarulhos", "Jardim Gumercindo");
        adicionarMarker(mMap, new LatLng(-23.472318, -46.531100), "Guarulhos", "Força Pública");
        adicionarMarker(mMap, new LatLng(-23.465164, -46.534394), "Guarulhos", "Assis Chateaubriand");
        adicionarMarker(mMap, new LatLng(-23.455032, -46.495834), "Guarulhos", "Odair Santanelli");
        adicionarMarker(mMap, new LatLng(-23.462330, -46.454227), "Cumbica", "Orlanda Bérgamo");
        adicionarMarker(mMap, new LatLng(-23.452436, -46.454839), "Cumbica", "Velha Guarulhos");
        adicionarMarker(mMap, new LatLng(-23.437483, -46.428880), "Parque Alvorada", "Santa Helena");
        adicionarMarker(mMap, new LatLng(-23.437887, -46.422550), "Parque Alvorada", "Carbonita");
        adicionarMarker(mMap, new LatLng(-23.430145, -46.432132), "Presidente Dutra", "Camamu");
        adicionarMarker(mMap, new LatLng(-23.433895, -46.433318), "Presidente Dutra", "Carmela Dutra");

        // Estilo Mapa
        try {
            boolean success = mMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style)
            );

            if (!success) {
                Toast.makeText(this, "Erro ao carregar estilo do mapa.", Toast.LENGTH_SHORT).show();
            }
        } catch (Resources.NotFoundException e) {
            Toast.makeText(this, "Arquivo de estilo não encontrado: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        LatLng saoPaulo = new LatLng(-23.55052, -46.633308);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(saoPaulo, 12));

        obterLocalizacaoAtual();

        // Limites da câmera
        LatLngBounds saoPauloBounds = new LatLngBounds(
                new LatLng(-24.5, -47.5),  // sudoeste (lat, long)
                new LatLng(-23.0, -46.0)   // nordeste (lat, long)
        );

        mMap.setLatLngBoundsForCameraTarget(saoPauloBounds);


        mMap.setMinZoomPreference(10);
        mMap.setMaxZoomPreference(18);


        mMap.setOnCameraMoveListener(() -> {
            if (!saoPauloBounds.contains(mMap.getCameraPosition().target)) {

                LatLng target = saoPauloBounds.contains(mMap.getCameraPosition().target)
                        ? mMap.getCameraPosition().target
                        : saoPaulo;
                mMap.moveCamera(CameraUpdateFactory.newLatLng(target));
            }
        });


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        mMap.setMyLocationEnabled(true);

        obterLocalizacaoAtual();

        mMap.setOnMarkerClickListener(marker -> {
            txtLocation.setText(marker.getTitle());
            mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
            return false;
        });
    }


    private void obterLocalizacaoAtual() {
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(this, location -> {
                    if (location != null) {
                        LatLng minhaLocalizacao = new LatLng(location.getLatitude(), location.getLongitude());
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(minhaLocalizacao, 50)); // Alterando o zoom
                    } else {
                        Toast.makeText(MapsActivity.this, "Não foi possível obter sua localização.", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(MapsActivity.this, "Erro ao obter localização: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }

    private void adicionarMarker(GoogleMap googleMap, LatLng local, String titulo, String descricao) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_marker);
        Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, 100, 100, false);

        // Maker
        googleMap.addMarker(new MarkerOptions()
                .position(local)
                .title(titulo)
                .snippet(descricao)
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                mMap.setMyLocationEnabled(true);
                obterLocalizacaoAtual();
            } else {
                Toast.makeText(this, "Permissão de localização negada.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
