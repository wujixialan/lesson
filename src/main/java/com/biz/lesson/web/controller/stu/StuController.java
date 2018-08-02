package com.biz.lesson.web.controller.stu;

import com.biz.lesson.Constants;
import com.biz.lesson.business.stu.ClazzService;
import com.biz.lesson.business.stu.LessionSerivce;
import com.biz.lesson.business.stu.StuService;
import com.biz.lesson.model.stu.Clazz;
import com.biz.lesson.model.stu.Lession;
import com.biz.lesson.model.stu.Stu;
import com.biz.lesson.util.CosUtil;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

/**
 * @author kevin zhao
 * @date 2018/7/25
 */
@Controller
@RequestMapping("/stu")
public class StuController {
    @Autowired
    private StuService stuService;
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private HttpServletRequest request;

    private static final Logger logger = LoggerFactory.getLogger(StuController.class);
    @Autowired
    private LessionSerivce lessionSerivce;

    /**
     * 跳转到对应的页面。
     *
     * @param flag
     * @return
     */
    @RequestMapping("/to/{id}/{flag}")
    public String to(@PathVariable("flag") String flag, @PathVariable("id") Integer id) {
        if (flag.trim().equals(Constants.ADD)) {
            List<Clazz> clazzes = clazzService.findAllClazz();
            request.setAttribute("clazzes", clazzes);
            return "kevin/stu/add";
        }
        if (flag.trim().equals(Constants.LIST)) {
            return "kevin/stu/list";
        }
        if (flag.trim().equals(Constants.EDIT)) {
            Stu stu = stuService.findById(id);
            List<Clazz> clazzes = clazzService.findAllClazz();
            request.setAttribute("clazzes", clazzes);
            request.setAttribute("stu", stu);
            return "kevin/stu/modify";
        }
        if (flag.trim().equals(Constants.CHOOSE_LIST)) {
            request.setAttribute("stuId", id);
            Stu stu = stuService.findById(id);
            request.setAttribute("stu", stu);
            return "kevin/stu/chooseList";
        }
        return null;
    }

    /**
     * 实现学生信息的添加。
     *
     * @param stu
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<Object, Object> add(Stu stu) {
        Map<Object, Object> map = new HashMap<>();
        try {
            stuService.add(stu);
            map.put("code", 200);
            map.put("msg", "保存成功");
        } catch (Exception e) {
            map.put("code", 400);
            map.put("msg", "保存失败");
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 查询所有学生的信息。
     *
     * @return
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Layui list(Integer page, Integer limit, String stuNum, String stuName, String birth) {
        LocalDate start = LocalDate.parse("1900-01-01"), end = LocalDate.parse("2050-12-31");
        if (birth != null) {
            String[] strings = birth.split("~");
            start = LocalDate.parse(strings[0].trim());
            end = LocalDate.parse(strings[1].trim());
        }
        Page<Stu> stuList = stuService.list(page, limit, stuNum, stuName, start, end);
        return Layui.data((int) stuList.getTotalElements(), stuList.getContent());
    }

    /**
     * 上传图片
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<Object, Object> upload(MultipartFile file) {
        Map<Object, Object> map = new HashMap<>();
        try {
            URL url = CosUtil.getFileInfo(file);
            logger.info(url.toString());
            map.put("code", 200);
            map.put("msg", url.toString());
        } catch (IOException e) {
            map.put("code", 200);
            map.put("msg", "上传错误");
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 修改学生信息
     *
     * @param stu
     * @return
     */
    @RequestMapping(value = "/modify", method = RequestMethod.PUT)
    @ResponseBody
    public Map<Object, Object> modify(Stu stu) {
        Map<Object, Object> map = new HashMap<>();
        try {
            stuService.modify(stu);
            map.put("code", 200);
            map.put("msg", "保存成功");
        } catch (Exception e) {
            map.put("code", 400);
            map.put("msg", "保存失败");
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 根据 id 删除学生信息。
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<Object, Object> del(Integer id) {
        Map<Object, Object> map = new HashMap<>();
        logger.debug("del------" + id);
        try {
            stuService.del(id);
            map.put("code", 200);
            map.put("msg", "删除成功");
        } catch (Exception e) {
            map.put("code", 400);
            map.put("msg", "删除失败");
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping(value = "/{id}/findAll", method = RequestMethod.GET)
    public String findAll(@PathVariable("id") Integer id) {
        Stu stu = stuService.findById(id);
        List<Lession> lessions = lessionSerivce.findAll();
        request.setAttribute("stu", stu);
        request.setAttribute("lessions", lessions);
        return "kevin/stu/choose";
    }

    @ResponseBody
    @RequestMapping(value = "/choose", method = RequestMethod.POST)
    public Map<Object, Object> choose(Integer stuId, String lessionIds) {
        Map<Object, Object> map = new HashMap<>();
        try {
            stuService.saveStuAndLession(stuId, lessionIds);

            map.put("code", 200);
            map.put("msg", stuId);
        } catch (Exception e) {
            map.put("code", 200);
            map.put("msg", "已经选过了");
        }
        return map;
    }

    @RequestMapping(value = "/{id}/chooseList")
    @ResponseBody
    public Layui chooseList(@PathVariable("id") Integer id) {
        List<Object[]> chooseList = stuService.chooseList(id);
        List<Lession> lessions = new ArrayList<>();
        chooseList.stream().forEach(ele -> {
            Lession lession = lessionSerivce.findById((Integer) ele[1]);
            lessions.add(lession);
        });
        return Layui.data(lessions.size(), lessions);
    }

    @ResponseBody
    @RequestMapping(value = "/enter", method = RequestMethod.POST)
    public Map<Object, Object> enter(Integer stuId, Integer lessionId, Float score) {
        logger.debug("------" + stuId + "-" + lessionId + "-" + score);
        Map<Object, Object> map = new HashMap<>();
        stuService.modifyScore(stuId, lessionId, score);
        map.put("code", 200);
        return map;
    }
}
