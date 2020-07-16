package jp.co.esm.miffy.Controller;

import jp.co.esm.miffy.entity.Asf4Member;
import jp.co.esm.miffy.form.ErrorCheck;
import jp.co.esm.miffy.service.Asf4MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;
import java.util.NoSuchElementException;

@Setter
@Getter
@Controller
@RequiredArgsConstructor
@SessionAttributes(types = Asf4Member.class)
public class MemberInfoController {
    private final Asf4MemberService asf4MemberService;
    /**
     * 入力フォームをリセットするかどうかを判定する変数
     */
    private boolean formReset = false;

    /**
     * entityオブジェクトを初期化して返却する
     *
     * @return Formオブジェクト
     */
    @ModelAttribute("asf4Member")
    public Asf4Member createAsf4Member() {
        Asf4Member asf4Member = new Asf4Member();
        return asf4Member;
    }

    /**
     * データ一覧画面に遷移する
     * entityオブジェクト"asf4Member"のIdにnullをsetすることで、新規登録の際に、idを指定せずにupdate()を
     * 実行するようにしている
     * @param model
     * @param asf4Member
     * @return
     */
    @RequestMapping("asf4members")
    public String index(Model model, Asf4Member asf4Member) {
        formReset = false;
        asf4Member.setId(null);
        List<Asf4Member> asf4MemberList = asf4MemberService.selectAll();
        model.addAttribute("asf4MemberList", asf4MemberList);
        // asf4MemberService.hook();    // デバッグ用のhookメソッドの呼び出し
        return "asf4members";
    }

    /**
     * 検索画面に遷移する
     * entityオブジェクト"asf4Member"のidobataIdに文字列"no"をsetすることで、検索画面でエラーメッセージが表示されないようにしている。
     *
     * @param asf4Member entityオブジェクト
     * @return 検索画面へのパス
     */
    @RequestMapping("/search")
    public String search(Asf4Member asf4Member, ErrorCheck errorCheck) {
        errorCheck.setCheck("no");
        asf4Member.setIdobataId("");
        return "search";
    }

    /**
     * 確認画面か検索画面に遷移する
     * 検索をして、テーブルに一致する項目がある場合は、確認画面に遷移する。
     * 検索をして、テーブルに一致する項目がない場合は、entityオブジェクト"asf4Member"のidobataIdに文字列"Yes"をsetすることで、
     * エラーメッセージ付きの検索画面に遷移する
     *
     * @param asf4Member entityオブジェクト
     * @return 確認画面か検索画面へのパス
     */
    @RequestMapping("/confirm")
    public String confirm(Asf4Member asf4Member, ErrorCheck errorCheck) {
        try {
            Asf4Member asf4MemberOptional = asf4MemberService.selectByidobataId(asf4Member.getIdobataId());
            asf4Member.setId(asf4MemberOptional.getId());
            asf4Member.setName(asf4MemberOptional.getName());
            asf4Member.setIdobataId(asf4MemberOptional.getIdobataId());
            asf4Member.setFloor(asf4MemberOptional.getFloor());
            asf4Member.setSkip(asf4MemberOptional.isSkip());
            formReset = true;
            return "confirm";
        } catch (NoSuchElementException e) {
            errorCheck.setCheck("Yes");
            System.out.println("エラーをcatchしました");
            return "search";
        }
    }

    /**
     * 登録画面に遷移する
     * 既存データを変更するときは、入力フォームをリセットしない
     * 新規登録の時は、入力フォームをリセットする
     *
     * @return 登録画面へのパス
     */
    @RequestMapping("/update")
    public String update(Asf4Member asf4Member) {
        if (!formReset) {
            asf4Member.setName("");
            asf4Member.setIdobataId("");
            asf4Member.setFloor("");
            asf4Member.setSkip(false);
        }
        return "update";
    }

    /**
     * 完了画面に遷移する
     *
     * @return データ一覧画面URLをリダイレクト先として指定したもの
     */
    @RequestMapping("/complete")
    public String complete(Asf4Member asf4Member) {
        asf4MemberService.update(asf4Member);
        return "redirect:/asf4members";
    }
}