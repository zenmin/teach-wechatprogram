package com.teach.wecharprogram.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.teach.wecharprogram.common.constant.CommonConstant;
import com.teach.wecharprogram.components.annotation.RequireRole;
import com.teach.wecharprogram.entity.DO.StudentPhysicalDo;
import com.teach.wecharprogram.entity.User;
import com.teach.wecharprogram.service.UserService;
import com.teach.wecharprogram.util.DateUtil;
import com.teach.wecharprogram.util.StaticUtil;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;
import com.teach.wecharprogram.common.ResponseEntity;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.StudentPhysical;
import com.teach.wecharprogram.service.StudentPhysicalService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Create by Code Generator
 *
 * @Author ZengMin
 * @Date 2019-07-15 20:15:32
 */

@Api(tags = "体适能及评测详情")
@RestController
@RequestMapping("/api/studentPhysical")
public class StudentPhysicalController {

    @Autowired
    StudentPhysicalService studentPhysicalService;

    @Autowired
    UserService userService;

    /**
     * 根据id查询一条数据
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "根据id查询一条数据", response = ResponseEntity.class)
    @ApiImplicitParam(name = "id", value = "主键", required = true)
    @PostMapping("/getOne")
    public ResponseEntity getOne(Long id) {
        return ResponseEntity.success(studentPhysicalService.getOne(id));
    }

    /**
     * 查全部 可带条件分页
     *
     * @param pager
     * @param studentPhysical
     * @return
     */
    @ApiOperation(value = "查全部 可带条件分页", response = ResponseEntity.class)
    @PostMapping("/listByPage")
    public ResponseEntity listByPage(Pager pager, StudentPhysicalDo studentPhysical) {
        return ResponseEntity.success(studentPhysicalService.listByPage(pager, studentPhysical));
    }

    /**
     * 带ID更新 不带ID新增
     *
     * @param studentPhysical
     * @return
     */
    @ApiOperation(value = "带ID更新 不带ID新增", response = ResponseEntity.class)
    @PostMapping("/save")
    @RequireRole({CommonConstant.ROLE_TRAIN})
    public ResponseEntity saveOrUpdate(StudentPhysical studentPhysical) {
        return ResponseEntity.success(studentPhysicalService.save(studentPhysical));
    }

    /**
     * 根据id删除   多个用,隔开
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "根据id删除 多个用,隔开", response = ResponseEntity.class)
    @ApiImplicitParam(name = "ids", value = "主键 多个用,隔开", required = true)
    @PostMapping("/delete")
    @RequireRole({CommonConstant.ROLE_ADMIN, CommonConstant.ROLE_TRAIN})
    public ResponseEntity delete(String ids) {
        return ResponseEntity.success(studentPhysicalService.delete(ids));
    }

    /**
     * 家长：表现五佳
     *
     * @return
     */
    @ApiOperation(value = "根据学生ID查询评测信息", response = ResponseEntity.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "studentId", value = "学生id", required = true),
            @ApiImplicitParam(name = "queryNow", value = "是否查询最近一次的评测默认true", required = true, dataType = "boolean")})
    @PostMapping("/getOneByStudent")
    public ResponseEntity getOneByStudent(@RequestParam(required = true) Long studentId, @RequestParam(defaultValue = "true") Boolean queryNow) {
        return ResponseEntity.success(studentPhysicalService.getOneByStudent(studentId, queryNow));
    }

    /**
     * 家长：表现五佳
     *
     * @return
     */
    @ApiOperation(value = "家长：表现3佳", response = ResponseEntity.class)
    @PostMapping("/topFive")
    public ResponseEntity topFive() {
        User loginUser = userService.getLoginUser();
        return ResponseEntity.success(studentPhysicalService.topFive(loginUser));
    }

    /**
     * 家长：表现五佳
     *
     * @return
     */
    @ApiOperation(value = "家长：进步3佳", response = ResponseEntity.class)
    @PostMapping("/topUpFive")
    public ResponseEntity topUpFive() {
        User loginUser = userService.getLoginUser();
        return ResponseEntity.success(studentPhysicalService.topUpFive(loginUser));
    }

    /**
     * 家长：查询最近一次的教练建议
     *
     * @return
     */
    @ApiOperation(value = "家长：查询最近一次的教练建议", response = ResponseEntity.class)
    @ApiImplicitParam(name = "studentIds", value = "学生id 多个用,隔开", required = true)
    @PostMapping("/getPhysicalByStudentIds")
    public ResponseEntity getPhysicalByStudentIds(String studentIds) {
        return ResponseEntity.success(studentPhysicalService.getPhysicalByStudentIds(studentIds));
    }

    /**
     * 查全部 可带条件分页
     *
     * @param physical
     * @return
     */
    @ApiOperation(value = "导出excel", response = StudentPhysicalDo.class)
    @ApiImplicitParam(name = "fields", value = "要导出的字段，多个用，隔开  为空导出全部")
    @PostMapping("/export")
    public void listByPage(StudentPhysicalDo physical, HttpServletResponse response, String fields) throws IOException {
        ExportParams exportParams = new ExportParams("体适能评估", "体适能评估(1)");
        if (StringUtils.isNotBlank(fields)) {
            exportParams.setExclusions(StaticUtil.getExportExcelField(StudentPhysicalDo.class, fields));   // 此处传入需要排除的字段
        }
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, StudentPhysicalDo.class, studentPhysicalService.listByPage(new Pager(0, Integer.MAX_VALUE), physical).getData());
        ServletOutputStream outputStream = response.getOutputStream();
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/msexcel");
        // 设置浏览器以下载的方式处理该文件名
        response.setHeader("Content-Disposition", "attachment;filename=" + DateUtil.getNowTime() + ".xls");
        workbook.write(outputStream);
        outputStream.close();
    }


}