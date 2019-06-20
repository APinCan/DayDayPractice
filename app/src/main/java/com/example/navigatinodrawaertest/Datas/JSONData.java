package com.example.navigatinodrawaertest.Datas;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONData {

    String JSONtext;

    public JSONData(){
        //테스트할 더미데이터
        JSONtext="[{'title':'배트맨','main':'ㅁㅈㄷㄻㅇㄴㄹ','address':'서울시 서울구','day':'2017-10-10'},"+
                "{'title':'슈퍼맨','main':'efaegwaef','address':'뉴욕시뉴욕구','day':'2055-66-22'},"+
                "{'title':'앤트맨','main':'veaw3zxcv12','address':'서울시 부산구','day':'212-1245-2421'}]";
    }

    //json텍스트로 들어온것을 parsing해야할경우
    public JSONData(String title, String main, String address, String day){
        sendObject(title, main, address, day);
    }

    /*
    데이터를 JSONObject로 보내고 받기
     */

    //일단 object안에 데이터를 넣음
//    public void sendObject(){
//        JSONObject jsonObject = new JSONObject();
//        try{
//            jsonObject.put("title", mJsonNationEt.getText().toString());
//            jsonObject.put("main", mJsonNameEt.getText().toString());
//            jsonObject.put("address", mJsonAddressEt.getText().toString());
//            jsonObject.put("day", mJsonAgeEt.getText().toString());
//            jsonObject.put("image", mJon.Age.getTExT().toString());
//
//        }catch (JSONException e){
//            e.printStackTrace();
//        }
//
//        receiveObject(jsonObject);
//    }
    public void sendObject(String title, String main, String addrss, String day){
        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject.put("title", title);
            jsonObject.put("main", main);
            jsonObject.put("address", addrss);
            jsonObject.put("day", day);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }


    public void receiveObject(JSONObject data){
        try{
            String title=data.getString("title");
            String main=data.getString("main");
            String address=data.getString("address");
            String day=data.getString("day");
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    /*
    데이터를 JSONArray로 보내고 받기
     */
    public void sendArray(){
        JSONObject wrapObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try{
            //각 정보마다 object로 만들어 Array에 담는 반복문
            for(int i = 0; i < 10; i++){
                //오브젝트를 생성하고
                JSONObject jsonObject = new JSONObject();
                //그 오브젝트안에 데이터를 String으로 담음
//                jsonObject.put("title", mJsonNationEt.getText().toString());
//                jsonObject.put("main", mJsonNameEt.getText().toString());
//                jsonObject.put("address", mJsonAddressEt.getText().toString());
//                jsonObject.put("day", mJsonAgeEt.getText().toString());
                //그 다음 array안에 안에 생성한 jsonObject를 넣음
                jsonArray.put(jsonObject);
            }
            //그리고 오브젝트안에 그 array를 이름을 정해 넣어줌
            wrapObject.put("list",jsonArray);

            //실제 데이터 전송 메소드
            receiveArray(wrapObject.toString());
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void receiveArray(String dataObject){
        try {
//            /*
//            array가 object로 감싸진경우, object가 array에 제목처럼
//             */
//            // String 으로 들어온 값 JSONObject 로 1차 파싱
//            JSONObject wrapObject = new JSONObject(dataObject);
//
//            // JSONObject 의 키 "list" 의 값들을 JSONArray 형태로 변환
//            JSONArray jsonArray = new JSONArray(wrapObject.getString("list"));

            /*
            array가 object로 감싸지지 않고 그냥 들어온경우 << 제목이 없는 위의 예제 에서는 제대로 작동
             */
            JSONArray jsonArray=new JSONArray(dataObject);

            for(int i = 0; i < jsonArray.length(); i++){
                // Array 에서 하나의 JSONObject 를 추출
                JSONObject dataJsonObject = jsonArray.getJSONObject(i);
                // 추출한 Object 에서 필요한 데이터를 표시할 방법을 정해서 화면에 표시
                String title=dataJsonObject.getString("title");
                Log.d("json", "title: "+title);
                String main=dataJsonObject.getString("main");
                Log.d("json", "main: "+main);
                String address=dataJsonObject.getString("address");
                Log.d("json", "addresss: "+address);
                String day=dataJsonObject.getString("day");
                Log.d("json", "day: "+day);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
