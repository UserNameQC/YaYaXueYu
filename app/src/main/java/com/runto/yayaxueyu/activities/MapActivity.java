package com.runto.yayaxueyu.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.AmapPageType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.runto.controler.utils.Entity;
import com.runto.yayaxueyu.R;
import com.runto.yayaxueyu.databinding.ActivityMapBinding;
import com.runto.yayaxueyu.utils.FileUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import me.shaohui.bottomdialog.BottomDialog;

@Route(path = "/main/mapActivity")
public class MapActivity extends BaseActivity {

    public ActivityMapBinding binding;
    public AMap aMap;
    public UiSettings uiSettings;
    public MyLocationStyle myLocationStyle;
    public AMapLocationClient mLocationClient;//声明mLocationClient对象
    public AMapLocationClientOption mLocationClientOption = null;//
    public CameraUpdate cameraUpdate;
    public String MapType;
    public MarkerOptions markerOptions;
    public String TAG = "MapActivity";
    public LatLng locationLatLng;
    public LatLng endLatLng;
    public BottomDialog bottomDialog;

    public String[] PERMISSIONS_STORAGE = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_map);
        binding.mapView.onCreate(savedInstanceState);
        getAssetsStyle();
        initPermission();
        initMapView();
        initLocation();
        initView();
    }

    public void initView() {
        MapType = getIntent().getExtras().getString(Entity.MAP_TYPE);
        if (MapType.equals(Entity.MAP_SCHOOL_DETAILS)) {
            //binding.mapBottomLayout.setVisibility(View.GONE);
            binding.mapBottomLayout1.setVisibility(View.VISIBLE);
        } else {
            //binding.mapBottomLayout.setVisibility(View.GONE);
            binding.mapBottomLayout1.setVisibility(View.GONE);
            initMarker();
        }
    }

    public Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    String[] mes = (String[]) msg.obj;
                    initBottomDialog(mes[0], mes[1]);
                    break;
            }
        }
    };

    public void initBottomDialog(final String title, final String locDetails) {
       BottomDialog.create(getSupportFragmentManager())
        .setLayoutRes(R.layout.map_bottom_layout)
                .setDimAmount(0)
                .setCancelOutside(false)
                .setViewListener(new BottomDialog.ViewListener() {
                    @Override
                    public void bindView(View v) {
                        ((TextView) v.findViewById(R.id.map_school_name)).setText(title);
                        ((TextView) v.findViewById(R.id.map_location)).setText(locDetails);
                        v.findViewById(R.id.map_click_navigation).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                initMapNavi();
                            }
                        });
                        v.findViewById(R.id.map_school_details).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    }
                })
                .show();
    }

    //开启导航组件（传入起点，终点）
    public void initMapNavi() {
        Poi start = new Poi("金域中心", locationLatLng, "");
        /**终点传入的是北京站坐标,但是POI的ID "B000A83M61"对应的是北京西站，所以实际算路以北京西站作为终点**/
        Poi end = new Poi("未知地点", endLatLng, "B000A83M61");
        AmapNaviPage.getInstance().showRouteActivity(this,
                new AmapNaviParams(start, null, end, AmapNaviType.DRIVER, AmapPageType.NAVI), naviInfoCallback);
    }


    //将自定义地图写入手机sd卡
    public void getAssetsStyle() {
        FileUtils.getInstance(this).copyAssetsToSD("style.data", "yayaxueyu/styles");
    }

    //初始化地图组件
    public void initMapView() {
        aMap = binding.mapView.getMap();
        cameraUpdate = CameraUpdateFactory.zoomTo(14);
        uiSettings = aMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(false);//不显示缩放按钮
        uiSettings.setZoomGesturesEnabled(true);//缩放手势
        uiSettings.setScrollGesturesEnabled(true);//滑动手势
        uiSettings.setRotateGesturesEnabled(false);//关闭旋转手势
        uiSettings.setTiltGesturesEnabled(false);//关闭倾斜手势
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(2000);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//连续定位、蓝点不会移动到地图中心点，定位点依照设备方向旋转，并且蓝点会跟随设备移动。
        aMap.setMyLocationStyle(myLocationStyle);
        //加载自定义地图图层
        //aMap.setCustomMapStylePath(Environment.getExternalStorageDirectory() + "/yayaxueyu/styles");
        aMap.moveCamera(cameraUpdate);
        aMap.setMapCustomEnable(true);//开启地图缩放
        aMap.setMyLocationEnabled(true);//开启定位
    }

    //给地图增加Marker
    public void initMarker() {
        //la:36.660422;lo:117.138789
        markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(37.660485, 117.138890));
        markerOptions.title("不知道哪个点");
        markerOptions.draggable(true);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(getResources(), R.drawable.icon_address)));
        aMap.addMarker(markerOptions);

        AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.drawable.icon_address_clicked)));
                Message message = new Message();
                message.what = 0;
                message.obj = new String[]{marker.getTitle(), marker.getId()};
                handler.sendMessage(message);
                endLatLng = marker.getPosition();
                return false;
            }
        };
        aMap.setOnMarkerClickListener(markerClickListener);
    }

    //获取当前定位信息
    public void initLocation() {
        mLocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationClientOption = new AMapLocationClientOption();
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        double la = aMapLocation.getLatitude();//获取纬度
                        double lo = aMapLocation.getLongitude();//获取经度
                        aMapLocation.getAccuracy();//获取精度信息
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date(aMapLocation.getTime());
                        df.format(date);//定位时间
                        locationLatLng = new LatLng(la, lo);
                        setMapCircle();
                        Log.e(TAG, "onLocationChanged: la:" + la + ";" + "lo:" + lo);
                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }
            }
        });
        mLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationClientOption.setInterval(2000);
        mLocationClient.setLocationOption(mLocationClientOption);
        mLocationClient.startLocation();
    }

    public void setMapCircle() {
        Circle circle = aMap.addCircle(new CircleOptions().
                center(locationLatLng).
                radius(3000).
                fillColor(Color.argb(1, 69, 134, 222)).
                strokeColor(Color.argb(68, 69, 134, 222)).
                strokeWidth(2));
    }

    public void initPermission() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 1);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        binding.mapView.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        binding.mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        binding.mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        binding.mapView.onSaveInstanceState(outState);
    }

    public INaviInfoCallback naviInfoCallback = new INaviInfoCallback() {
        @Override
        public void onInitNaviFailure() {

        }

        @Override
        public void onGetNavigationText(String s) {

        }

        @Override
        public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

        }

        @Override
        public void onArriveDestination(boolean b) {

        }

        @Override
        public void onStartNavi(int i) {

        }

        @Override
        public void onCalculateRouteSuccess(int[] ints) {

        }

        @Override
        public void onCalculateRouteFailure(int i) {

        }

        @Override
        public void onStopSpeaking() {

        }

        @Override
        public void onReCalculateRoute(int i) {

        }

        @Override
        public void onExitPage(int i) {

        }

        @Override
        public void onStrategyChanged(int i) {

        }

        @Override
        public View getCustomNaviBottomView() {
            return null;
        }

        @Override
        public View getCustomNaviView() {
            return null;
        }

        @Override
        public void onArrivedWayPoint(int i) {

        }
    };
}
