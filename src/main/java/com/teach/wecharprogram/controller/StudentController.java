package com.teach.wecharprogram.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.teach.wecharprogram.common.constant.CommonConstant;
import com.teach.wecharprogram.components.annotation.RequireRole;
import com.teach.wecharprogram.entity.DO.StudentDo;
import com.teach.wecharprogram.entity.User;
import com.teach.wecharprogram.service.UserService;
import com.teach.wecharprogram.util.StaticUtil;
import io.swagger.annotations.*;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import com.teach.wecharprogram.common.ResponseEntity;
import com.teach.wecharprogram.entity.DO.Pager;
import com.teach.wecharprogram.entity.Student;
import com.teach.wecharprogram.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Objects;


/**
 * Create by Code Generator
 *
 * @Author ZengMin
 * @Date 2019-06-15 18:17:01
 */

@Api(tags = "学生管理")
@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    StudentService studentService;

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
        return ResponseEntity.success(studentService.getOne(id));
    }

    /**
     * 查询全部 可带条件
     *
     * @param student
     * @return
     */
    @ApiOperation(value = "查询全部 可带条件", response = ResponseEntity.class)
    @PostMapping("/list")
    public ResponseEntity list(Student student) {
        return ResponseEntity.success(studentService.list(student));
    }

    /**
     * 查全部 可带条件分页
     *
     * @param pager
     * @param student
     * @return
     */
    @ApiOperation(value = "查全部 可带条件分页", response = ResponseEntity.class)
    @PostMapping("/listByPage")
    public ResponseEntity listByPage(Pager pager, Student student) {
        return ResponseEntity.success(studentService.listByPage(pager, student));
    }

    /**
     * 查全部 可带条件分页
     *
     * @param pager
     * @return
     */
    @ApiOperation(value = "查全部 可带条件分页", response = ResponseEntity.class)
    @PostMapping("/getStudentsBySchool")
    public ResponseEntity getStudentsBySchool(Pager pager, Long schoolId) {
        return ResponseEntity.success(studentService.getStudentsBySchool(pager, schoolId));
    }

    /**
     * 带ID更新 不带ID新增
     *
     * @param student
     * @return
     */
    @ApiOperation(value = "带ID更新 不带ID新增", response = ResponseEntity.class)
    @PostMapping("/save")
    @RequireRole({CommonConstant.ROLE_FAMILY, CommonConstant.ROLE_TEACHER, CommonConstant.ROLE_HEADMASTER})
    public ResponseEntity saveOrUpdate(Student student, HttpServletRequest servletRequest) {
        StaticUtil.validateField(student.getName());
        StaticUtil.validateObject(student.getStatus());
        User user = userService.getLoginUser(servletRequest);
        // 如果当前用户是家长 不允许修改编辑
        if (user.getRoleCode().equals(CommonConstant.ROLE_FAMILY)) {
            student.setClassesId(null);
            student.setClassesName(null);
        }
        return ResponseEntity.success(studentService.save(student));
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
    public ResponseEntity delete(String ids) {
        return ResponseEntity.success(studentService.delete(ids));
    }


    /**
     * 家长关联学生
     *
     * @return
     */
    @ApiOperation(value = "家长关联学生", response = ResponseEntity.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "studentsId", value = "学生id,多个用，隔开", required = true),
            @ApiImplicitParam(name = "userId", value = "用户id （本人操作可不传）")})
    @PostMapping("/relFamilyToStudent")
    public ResponseEntity relFamilyToStudent(Long userId, String studentsId, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        if (Objects.isNull(userId)) {
            userId = loginUser.getId();
        }
        return ResponseEntity.success(studentService.relFamilyToStudent(userId, studentsId));
    }

    /**
     * 家长关联学生
     *
     * @return
     */
    @ApiOperation(value = "家长删除关联学生", response = ResponseEntity.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "studentId", value = "学生id", required = true),
            @ApiImplicitParam(name = "userId", value = "用户id （本人操作可不传）")})
    @PostMapping("/delRelFamilyToStudent")
    @RequireRole({CommonConstant.ROLE_FAMILY, CommonConstant.ROLE_TEACHER})
    public ResponseEntity delRelFamilyToStudent(Long userId, String studentId, HttpServletRequest request) {
        StaticUtil.validateField(studentId);
        User loginUser = userService.getLoginUser(request);
        if (Objects.isNull(userId)) {
            userId = loginUser.getId();
        }
        return ResponseEntity.success(studentService.delRelFamilyToStudent(userId, studentId));
    }

    /**
     * 查全部 可带条件分页
     *
     * @param studentDo
     * @return
     */
    @ApiOperation(value = "导出excel", response = ResponseEntity.class)
    @PostMapping("/export")
    public void export(StudentDo studentDo, HttpServletResponse response) throws IOException {
        ExportParams exportParams = new ExportParams("学生基本信息", "学生基本信息(1)");
//        exportParams.setAddIndex(true);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, StudentDo.class, studentService.getStudentAllMsg(studentDo));
        ServletOutputStream outputStream = response.getOutputStream();
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/msexcel");
        // 设置浏览器以下载的方式处理该文件名
        response.setHeader("Content-Disposition", "attachment;filename=".concat(String.valueOf(URLEncoder.encode("学生基本信息.xls", "UTF-8"))));
        workbook.write(outputStream);
        outputStream.close();
    }
}