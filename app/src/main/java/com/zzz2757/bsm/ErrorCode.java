package com.zzz2757.bsm;

import android.content.Context;
import android.widget.Toast;

public class ErrorCode{
    public static void errorCode(Context context, int status, int subStatus){
        String[][] error_msg = {
            {
                "서버와의 연결에 실패하였습니다.",
            },
            {
                "정상 처리되었습니다.",
            },
            {
                "서버에 문제가 발생하였습니다.",
                "로그인 세션 저장에 실패하였습니다.",
                "회원가입중 알 수 없는 문제가 발생하였습니다.",
                "비밀번호 수정에 실패하였습니다.",
                "파일 업로드에 실패하였습니다.",
                "게시글 작성에 실패하였습니다.",
            },
            {
                "정상적인 접근이 아닙니다.",
                "권한이 없습니다.",
                "유효한 코드가 아닙니다.",
                "멤버코드가 없습니다.",
                "검색어가 없습니다.",
                "잘못된 검색 대상입니다.",
                "삭제된 게시글이거나 게시글이 없습니다.",
                "작성자가 아닙니다.",
                "학생정보가 맞지 않습니다.",
                "인증코드 전송에 실패하였습니다.",
            },
            {
                "계정이 정지되었습니다.",
                "로그인후 이용 가능 합니다.",
                "비밀번호 재설정이 필요합니다.",
                "만료된 코드입니다, 회원가입된 계정으로 로그인해주세요.",
            },
            {
                "id 또는 password가 맞지 않습니다.",
                "비밀번호 재입력이 맞지 않습니다.",
                "이미 사용중인 id입니다.",
                "이미 사용중인 닉네임입니다.",
                "수정할 비밀번호 재입력이 맞지 않습니다.",
            },
        };
        Toast.makeText(context, "에러코드 "+status+"_"+subStatus+"\n"+ error_msg[status][subStatus], Toast.LENGTH_SHORT).show();
    }
}
