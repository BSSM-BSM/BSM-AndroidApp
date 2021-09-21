package com.zzz2757.bsm;

import android.content.Context;
import android.widget.Toast;

public class ErrorCode{
    public static void errorCode(Context context, int error_code){
        String[] error_msg = {
                "", "",
                "정상적인 접근이 아닙니다.",
                "로그인 세션 저장에 실패하였습니다.",
                "id 또는 password가 맞지 않습니다.",
                "비밀번호 재입력이 맞지 않습니다.",
                "이미 사용중인 id입니다.",
                "이미 사용중인 닉네임입니다.",
                "계정 인증이 필요합니다.",
                "유효한 코드가 아닙니다.",
                "만료된 코드입니다, 새로운 코드를 발급받아 주세요.",
                "회원가입중 알 수 없는 문제가 발생하였습니다.",
                "수정할 비밀번호 재입력이 맞지 않습니다.",
                "비밀번호 수정에 실패하였습니다.",
                "멤버코드가 없습니다.",
                "검색어가 없습니다.",
                "잘못된 검색 대상입니다.",
                "게시글 번호가 없습니다.",
                "삭제된 게시글 입니다.",
                "정상적인 접근이 아닙니다 로그인 해주세요.",
                "게시글 작성자가 아닙니다.",
                "로그인후 이용 가능 합니다.",
        };
        Toast.makeText(context, "에러코드 "+error_code+"\n"+ error_msg[error_code], Toast.LENGTH_SHORT).show();
    }
}
