    package com.example.main_;

    import androidx.annotation.Nullable;

    import com.android.volley.AuthFailureError;
    import com.android.volley.Response;
    import com.android.volley.toolbox.StringRequest;

    import java.util.HashMap;
    import java.util.Map;

    public class RegisterRequest extends StringRequest {

        //서버 URL설정 (PHP파일 연동)
        final static private String URL = "http://strawberry.dothome.co.kr/registerget2.php";
        private Map<String, String> map;

        public RegisterRequest(String user_id, String user_password, String user_name, String user_phoneNum, String user_nickname, String user_email, String user_career, Response.Listener<String> listener) {
            super(Method.POST, URL, listener, null);

            map = new HashMap<>();
            map.put("user_id", user_id); // 유저 아이디
            map.put("user_pw", user_password); // 유저 비밀번호
            map.put("user_name", user_name); // 유저 이름
            map.put("user_phoneNum", user_phoneNum); // 유저 전화번호
            map.put("user_nickName", user_nickname); // 유저 닉네임
            map.put("user_email", user_email); // 유저 이메일
            map.put("user_career",user_career); // 유저 경력사항

            System.out.println(map.get("user_id"));
        }

        @Nullable
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return map;
        }
    }
