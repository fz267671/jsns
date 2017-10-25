package com.lxinet.jeesns.web.front;

import com.lxinet.jeesns.model.common.Link;
import com.lxinet.jeesns.service.common.IArchiveService;
import com.lxinet.jeesns.common.utils.EmojiUtil;
import com.lxinet.jeesns.common.utils.MemberUtil;
import com.lxinet.jeesns.core.dto.ResponseModel;
import com.lxinet.jeesns.core.model.Page;
import com.lxinet.jeesns.core.utils.Const;
import com.lxinet.jeesns.core.utils.ErrorUtil;
import com.lxinet.jeesns.core.utils.JeesnsConfig;
import com.lxinet.jeesns.service.common.ILinkService;
import com.lxinet.jeesns.service.group.IGroupFansService;
import com.lxinet.jeesns.service.group.IGroupService;
import com.lxinet.jeesns.service.group.IGroupTopicService;
import com.lxinet.jeesns.service.member.IMemberFansService;
import com.lxinet.jeesns.web.common.BaseController;
import com.lxinet.jeesns.model.member.Member;
import com.lxinet.jeesns.service.member.IMemberService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;

@Controller("indexController")
@RequestMapping("/")
public class IndexController extends BaseController{

    @Resource
    private IGroupTopicService groupTopicService;
    @Resource
    private IGroupService groupService;
    @Resource
    private IMemberService memberService;
    @Resource
    private IArchiveService archiveService;
    @Resource
    private JeesnsConfig jeesnsConfig;
    @Resource
    private IGroupFansService groupFansService;
    @Resource
    private IMemberFansService memberFansService;
    @Resource
    private ILinkService linkService;

    @RequestMapping(value={"/", "index"},method = RequestMethod.GET)
    public String index(@RequestParam(value = "key",required = false,defaultValue = "") String key, Integer cateid,Model model) {
        Page page = new Page(request);
        if(cateid == null){
            cateid = 0;
        }
        Member loginMember = MemberUtil.getLoginMember(request);
        int loginMemberId = loginMember == null ? 0 : loginMember.getId();
        ResponseModel groupTopicModel = groupTopicService.listByPage(page,key,cateid,1,0);
        ResponseModel groupModel = groupService.listByPage(1,page,key);
        ResponseModel linkModel = linkService.recommentList();
        page.setPageSize(50);
        model.addAttribute("groupTopicModel",groupTopicModel);
        model.addAttribute("groupModel",groupModel);
        model.addAttribute("linkModel",linkModel);

        return jeesnsConfig.getFrontTemplate() + "/index";
    }

    @RequestMapping(value = "u/{id}",method = RequestMethod.GET)
    public String u(@PathVariable("id") Integer id, Model model){
        Page page = new Page(request);
        Member member = memberService.findById(id);
        if(member == null){
            return jeesnsConfig.getFrontTemplate() + ErrorUtil.error(model,-1005, Const.INDEX_ERROR_FTL_PATH);
        }
        model.addAttribute("member",member);
        Member loginMember = MemberUtil.getLoginMember(request);
        model.addAttribute("loginMember", loginMember);
        return jeesnsConfig.getFrontTemplate() + "/u";
    }

    @RequestMapping(value = "u/{id}/home/{type}",method = RequestMethod.GET)
    public String home(@PathVariable("id") Integer id, @PathVariable("type") String type, Model model){
        Page page = new Page(request);
        Member member = memberService.findById(id);
        if(member == null){
            return jeesnsConfig.getFrontTemplate() + ErrorUtil.error(model,-1005, Const.INDEX_ERROR_FTL_PATH);
        }
        model.addAttribute("member",member);

        Member loginMember = MemberUtil.getLoginMember(request);
        model.addAttribute("loginMember", loginMember);
        int loginMemberId = 0;
        if(loginMember != null){
            loginMemberId = loginMember.getId().intValue();
        }
        if("groupTopic".equals(type)){
            model.addAttribute("model", groupTopicService.listByPage(page,"",0,1, id));
        } else if("group".equals(type)){
            model.addAttribute("model", groupFansService.listByMember(page, id));
        } else if("follows".equals(type)){
            model.addAttribute("model", memberFansService.followsList(page,id));
        } else if("fans".equals(type)){
            model.addAttribute("model", memberFansService.fansList(page,id));
        }
        model.addAttribute("type",type);
        return jeesnsConfig.getFrontTemplate() + "/home";
    }


    @RequestMapping(value = "newVersion",method = RequestMethod.GET)
    @ResponseBody
    public String newVersion(@RequestParam("callback") String callback){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("LAST_SYSTEM_VERSION",Const.LAST_SYSTEM_VERSION);
        jsonObject.put("LAST_SYSTEM_UPDATE_TIME", Const.LAST_SYSTEM_UPDATE_TIME);
        return callback + "(" + jsonObject.toString() + ")";
    }
    /**
     * 获取Emoji数据
     * @return
     */
    @RequestMapping(value="/emoji/emojiJsonData.json",method = RequestMethod.GET)
    @ResponseBody
    public Object emojiJsonData(){
        return EmojiUtil.emojiJson();
    }

    @RequestMapping(value="/404",method = RequestMethod.GET)
    public String jeesns404(){
        return jeesnsConfig.getFrontTemplate() + "/common/404";
    }

    @RequestMapping(value="/error",method = RequestMethod.GET)
    public String error(){
        return jeesnsConfig.getFrontTemplate() + "/common/error";
    }

    /**
     * 友情链接
     * @param model
     * @return
     */
    @RequestMapping(value={"/link"},method = RequestMethod.GET)
    public String link(Model model) {
        ResponseModel linkModel = linkService.allList();
        model.addAttribute("linkModel",linkModel);
        return jeesnsConfig.getFrontTemplate() + "/link";
    }
}
