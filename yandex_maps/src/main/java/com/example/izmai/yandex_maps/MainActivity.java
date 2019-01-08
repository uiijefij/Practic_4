package com.example.izmai.yandex_maps;

import android.graphics.Color;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;
import com.yandex.mapkit.user_location.UserLocationObjectListener;
import com.yandex.mapkit.user_location.UserLocationView;
import com.yandex.runtime.image.ImageProvider;

public class MainActivity extends AppCompatActivity implements UserLocationObjectListener {

    private MapView mapView;
    private UserLocationLayer userLocationLayer;
    private final String MAPKIT_API_KEY = "dfba783a-35e1-4a86-b0c9-dce351a55a8b";

    @Override
    public void onObjectAdded(@NonNull UserLocationView userLocationView) {
        userLocationLayer.setAnchor(
                new PointF((float) (mapView.getWidth() * 0.5), (float)
                        (mapView.getHeight() * 0.5)),
                new PointF((float) (mapView.getWidth() * 0.5), (float)
                        (mapView.getHeight() * 0.83)));
// При определении направления движения устанавливается следующая иконка
        userLocationView.getArrow().setIcon(ImageProvider.fromResource(
                this, R.drawable.yandex_logo_ru));
// При получении координат местоположения устанавливается следующая
        userLocationView.getPin().setIcon(ImageProvider.fromResource(
                this, R.drawable.yandex_logo_ru));
// Обозначается точность определения местоположения с помощью окружности
        userLocationView.getAccuracyCircle().setFillColor(Color.BLUE);
    }
    @Override
    public void onObjectRemoved(@NonNull UserLocationView userLocationView) {
    }
    @Override
    public void onObjectUpdated(@NonNull UserLocationView userLocationView,
                                @NonNull ObjectEvent objectEvent) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/**
 * Задайте API-ключ перед инициализацией MapKitFactory.
 * Рекомендуется устанавливать ключ в методе Application.onCreate,
 * но в данном примере он устанавливается в activity.
 */
        MapKitFactory.setApiKey(MAPKIT_API_KEY);
/**
 * Инициализация библиотеки для загрузки необходимых нативных библиотек.
 * Рекомендуется инициализировать библиотеку MapKit в методе
 * Activity.onCreate.
 * Инициализация в методе Application.onCreate может привести к лишним
 вызовам и увеличенному использованию батареи.
 */
        MapKitFactory.initialize(this);
        setContentView(R.layout.activity_main);
// Укажите имя activity
        mapView = findViewById(R.id.mapview);
// Устанавливаем начальную точку и масштаб
       // mapView.getMap().move(new CameraPosition(new Point(0, 0), 14, 0, 0));
// Установка слоя для отрисовки пользовательского местоположения
        userLocationLayer = mapView.getMap().getUserLocationLayer();
        userLocationLayer.setEnabled(true);
        userLocationLayer.setAutoZoomEnabled(true);
        userLocationLayer.setHeadingEnabled(true);
        userLocationLayer.setObjectListener(this);
    }
    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }
    @Override
    protected void onStop() {
        super.onStop();
        MapKitFactory.getInstance().onStop();
        mapView.onStop();
    }

}
