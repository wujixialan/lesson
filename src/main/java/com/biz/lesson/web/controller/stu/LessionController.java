package com.biz.lesson.web.controller.stu;

import com.biz.lesson.Constants;
import com.biz.lesson.business.stu.LessionSerivce;
import com.biz.lesson.model.stu.Lession;
import com.biz.lesson.util.Layui;
import com.biz.lesson.util.ReturnValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kevin zhao
 * @date 2018/7/28
 */
@Controller
@RequestMapping(value = "/lession")
public class LessionController {

    @Autowired
    private LessionSerivce lessionSerivce;
    @Autowired
    private HttpServletRequest request;
    private static final Logger logger = LoggerFactory.getLogger(LessionController.class);


    @RequestMapping(value = "/to/{id}/{flag}")
    public String to(@PathVariable("id") Integer id, @PathVariable("flag") String flag) {
        if (flag.trim().equals(Constants.LIST)) {
            return "kevin/lession/list";
        }
        if (flag.trim().equals(Constants.ADD)) {
            return "kevin/lession/add";
        }
        if (flag.trim().equals(Constants.EDIT)) {
            Lession lession = lessionSerivce.findById(id);
            request.setAttribute("lession", lession);
            return "kevin/lession/modify";
        }
        return null;
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public Layui list(Integer page, Integer limit) {
        Page<Lession> lessionPage = lessionSerivce.findByPage(page, limit);
        return Layui.data((int) lessionPage.getTotalElements(), lessionPage.getContent());
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<Object, Object> add(Lession lession) {
        Map<Object, Object> map = new HashMap<>();
        try {
            lessionSerivce.add(lession);
            map.put("code", 200);
            map.put("msg", "保存成功");
        } catch (Exception e) {
            map.put("code", 400);
            map.put("msg", "保存失败");
        }
        return map;
    }

    @RequestMapping(value = "/modify", method = RequestMethod.PUT)
    @ResponseBody
    public Map<Object, Object> modify(Lession lession) {
        Map<Object, Object> map = new HashMap<>();
        try {
            lessionSerivce.modify(lession);
            logger.debug("modify");
            map.put("code", 200);
            map.put("msg", "修改成功");
        } catch (Exception e) {
            map.put("code", 400);
            map.put("msg", "修改失败");
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/del", method = RequestMethod.DELETE)
    public Map<Object, Object> del(Integer id) {
        Map<Object, Object> map = new HashMap<>();
        try {
            lessionSerivce.del(id);
            map.put("code", 200);
            map.put("msg", "删除成功");
        } catch (Exception e) {
            map.put("code", 400);
            map.put("msg", "删除失败");
        }
        return map;
    }
}
