package com.bdlm.yytx.module.map;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;

import com.bdlm.yytx.R;
import com.bdlm.yytx.base.BaseActivity;
import com.trsoft.app.lib.view.recycleview.adapter.BaseRecycleViewAdapter;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 搜索地址 高德poi
 */

public class CSearchAreaActivity extends BaseActivity {

    @BindView(R.id.et_search_key)
    EditText etSearchKey;

    @BindView(R.id.rv)
    RecyclerView rv;
    private String searchKey;
    private int currentPage = 1;//当前搜索到的页码
    private List<PoiItem> listPoiItem = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_search_area;
    }

    @Override
    protected void createPersenter() {
        initListView();
    }


    /**
     * 初始化列表
     */
    private void initListView() {

        rv.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        rv.setHasFixedSize(true);

//       new  BaseRecycleViewAdapter<PoiItem>(listPoiItem,);

//        adapter = new SearchAreaXrvAdapter();
//        rv.setAdapter(adapter);
//
        etSearchKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                if (adapter != null && listPoiItem != null) {
//                    listPoiItem.clear();
////                    adapter.clear();
//                }
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                searchKey = s.toString();
                search(searchKey);
            }
        });
//           adapter.setItemClickListener(new SearchAreaXrvAdapter.OnItemClickListener() {
//               @Override
//               public void onItemClick(PoiItem poiItem) {
//                   Intent intent = new Intent(ctx, CommonSpecialCarActivity.class);
//                   intent.putExtra("poi",poiItem);
//                   setResult(1000,intent);
//                   finish();
//               }
//           });
    }


    private void search(String keyWord) {
        final PoiSearch.Query query = new PoiSearch.Query(keyWord, "", "");
//keyWord表示搜索字符串，
//第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
//cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);//设置查询页码
        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult result, int rCode) {
//                LogMe.e("搜索结果页数" + result.getPageCount() + "-----" + rCode);
                //解析result获取POI信息
                if (rCode == 1000) {
                    //请求成功
                    if (result != null && result.getQuery() != null) {// 搜索poi的结果
                        if (result.getQuery().equals(query)) {// 是否是同一条
                            PoiResult poiResult = result;
                            // 取得搜索到的poiitems有多少页
                            List<PoiItem> poiItems = poiResult.getPois();// 取得第一页的poiitem数据，页数从数字0开始

//                            List<SuggestionCity> suggestionCities = poiResult.getSearchSuggestionCitys();// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
                            resultSearch(poiItems, currentPage); //回调
                        }
                    }
                }
            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

            }
        });
        poiSearch.searchPOIAsyn();
    }

    //搜索回调 数据  并转换
    private synchronized void resultSearch(List<PoiItem> poiItems, int pageNum) {
        if (pageNum <= 0) {
            listPoiItem.clear();
        }
//        if (poiItems.size() <= 0) {
//            DialogUtil.showToastCust("没有更多了");
//        }
        listPoiItem.addAll(poiItems);
//        adapter.setDatas(listPoiItem);
    }
//   @Event(R.id.c_choice_employee_search_rl_back)
//    private  void back(View view){
//        onBackPressed();
//   }
}
