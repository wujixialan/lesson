package com.biz.lesson.web.controller.stu;

import com.biz.lesson.Constants;
import com.biz.lesson.business.stu.ClazzService;
import com.biz.lesson.model.stu.Clazz;
import com.biz.lesson.util.Layui;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kevin zhao
 * @date 2018/7/28
 */
@Controller
@RequestMapping(value = "/clazz")
public class ClazzController {
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private HttpServletRequest request;
    private static final Logger logger = LoggerFactory.getLogger(ClazzController.class);


    /**
     * 去到那个页面
     *
     * @param id
     * @param flag
     * @return
     */
    @RequestMapping("/to/{id}/{flag}")
    public String to(@PathVariable("id") Integer id, @PathVariable("flag") String flag) {
        if (flag.trim().equals(Constants.LIST)) {
            return "kevin/clazz/list";
        }
        if (flag.trim().equals(Constants.ADD)) {
            return "kevin/clazz/add";
        }
        if (flag.trim().equals(Constants.EDIT)) {
            Clazz clazz = clazzService.findById(id);
            request.setAttribute("clazz", clazz);
            return "kevin/clazz/modify";
        }
        return null;
    }

    /**
     * 通过分页查找所有的班级信息。
     *
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Layui list(Integer page, Integer limit) {
        Page<Clazz> clazzPage = clazzService.list(page, limit);
        return Layui.data((int) clazzPage.getTotalElements(), clazzPage.getContent());
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<Object, Object> add(Clazz clazz) {
        Map<Object, Object> map = new HashMap<>();
        try {
            clazzService.add(clazz);
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
    public Map<Object, Object> modify(Clazz clazz) {
        Map<Object, Object> map = new HashMap<>();
        logger.info(clazz.toString());
        logger.info(clazz.getClazzName());
        try {
            clazzService.modify(clazz);
            map.put("code", 200);
            map.put("msg", "修改成功");
        } catch (Exception e) {
            map.put("code", 400);
            map.put("msg", "修改失败");
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping(value = "/del", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<Object, Object> del(Integer id) {
        Map<Object, Object> map = new HashMap<>();
        try {
            clazzService.del(id);
            map.put("code", 200);
            map.put("msg", "删除成功");
        } catch (Exception e) {
            map.put("code", 400);
            map.put("msg", "删除失败");
            e.printStackTrace();
        }
        return map;
    }
}
