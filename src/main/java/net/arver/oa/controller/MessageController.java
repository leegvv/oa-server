package net.arver.oa.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.arver.oa.common.util.R;
import net.arver.oa.config.shiro.JwtUtil;
import net.arver.oa.form.DeleteMessageRefByIdForm;
import net.arver.oa.form.SearchMessageByIdForm;
import net.arver.oa.form.SearchMessageByPageForm;
import net.arver.oa.form.UpdateUnreadMessageForm;
import net.arver.oa.service.MessageService;
import net.arver.oa.task.MessageTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * 消息controller.
 * @author leegvv
 */
@RestController
@RequestMapping("/message")
@Api("消息模块网络接口")
public class MessageController {

    /**
     * jwt工具类.
     */
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 消息服务.
     */
    @Autowired
    private MessageService messageService;

    /**
     * 消息任务.
     */
    @Autowired
    private MessageTask messageTask;

    /**
     * 获取分忧消息列表.
     * @param params 参数
     * @param token token
     * @return 消息列表
     */
    @GetMapping("/searchMessageByPage")
    @ApiOperation("获取分页消息列表")
    public R<List<HashMap>> searchMessageByPage(@Valid final SearchMessageByPageForm params,
                                                @RequestHeader final String token) {
        final int userId = jwtUtil.getUserId(token);
        final Integer page = params.getPage();
        final Integer length = params.getLength();
        final long start = (page - 1) * length;
        final List<HashMap> list = messageService.searchMessageByPage(userId, start, length);
        return R.ok(list);
    }

    /**
     * 根据ID查询消息.
     * @param params 查询条件
     * @return 消息
     */
    @GetMapping("/searchMessageById")
    @ApiOperation("根据ID查询消息")
    public R searchMessageById(@Valid final SearchMessageByIdForm params) {
        final HashMap map = messageService.searchMessageById(params.getId());
        return R.ok(map);
    }

    /**
     * 未读消息更新为已读消息.
     * @param params 参数
     * @return 是否成功
     */
    @PostMapping("/updateUnreadMessage")
    @ApiOperation("未读消息更新为已读消息")
    public R updateUnreadMessage(@Valid @RequestBody final UpdateUnreadMessageForm params) {
        final long count = messageService.updateUnreadMessage(params.getId());
        return R.ok(count == 1);
    }

    /**
     * 删除消息.
     * @param params 查询条件
     * @return 是否成功
     */
    @PostMapping("/deleteMessageRefById")
    @ApiOperation("删除消息")
    public R deleteMessageRefById(@Valid @RequestBody final DeleteMessageRefByIdForm params) {
        final long count = messageService.deleteMessageRefById(params.getId());
        return R.ok(count == 1);
    }

    /**
     * 刷新消息.
     * @param token token
     * @return 消息信息
     */
    @GetMapping("/refreshMessage")
    @ApiOperation("刷新消息")
    public R refreshMessage(@RequestHeader final String token) {
        final int userId = jwtUtil.getUserId(token);
        // 异步接收消息
        messageTask.receiveAsync(userId + "");
        // 查询接收了多少条消息
        final long lastRows = messageService.searchLastCount(userId);
        // 查询未读数据
        final long unreadRows = messageService.searchUnreadCount(userId);
        return R.ok().setAdditionalProperties("lastRows", lastRows).setAdditionalProperties("unreadRows", unreadRows);

    }
}
