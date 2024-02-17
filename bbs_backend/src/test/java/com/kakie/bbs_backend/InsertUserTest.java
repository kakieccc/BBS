package com.kakie.bbs_backend;

import com.kakie.bbs_backend.model.domain.User;
import com.kakie.bbs_backend.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.util.StopWatch;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * @desc 批量插入假数据，没什么用，玩玩多线程和并发（
 */
@SpringBootTest
public class InsertUserTest {
    @Resource
    private UserService userService;

//    //线程设置
//    private ExecutorService executorService = new ThreadPoolExecutor(16, 1000, 10000, TimeUnit.MINUTES, new ArrayBlockingQueue<>(10000));
    /**
     * 多线程并发批量插入用户   100000
     */
    @Test
    public void doConcurrencyInsertUser() {
//        StopWatch stopWatch = new StopWatch();
//        stopWatch.start();
        final int INSERT_NUM = 100000;
        int j = 0;
        //批量插入数据的大小
        int batchSize = 5000;
        List<CompletableFuture<Void>> futureList = new ArrayList<>();
        //根据数据量和插入批量来计算需要循环的次数。
        for (int i = 0; i < INSERT_NUM / batchSize; i++) {
            //用多线程记得不能使用不安全的集合，得转换一下
            List<User> userList = Collections.synchronizedList(new ArrayList<>());
            while (true) {
                j++;
                User user = new User();
                user.setUserName("假数据");
                user.setUserAccount("fakeaccount");
                user.setAvatarUrl("https://img0.baidu.com/it/u=3514514443,3153875602&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500");
                user.setGender(0);
                user.setUserPassword("231313123");
                user.setPhone("1231312");
                user.setEmail("12331234@qq.com");
                user.setUserStatus(0);
                user.setUserRole(0);
                user.setTags("[]");
                userList.add(user);
                if (j % batchSize == 0) {
                    break;
                }
            }
            //异步执行 使用CompletableFuture开启异步任务
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                System.out.println("ThreadName：" + Thread.currentThread().getName());
                userService.saveBatch(userList, batchSize);
            });
            futureList.add(future);
        }
        CompletableFuture.allOf(futureList.toArray(new CompletableFuture[]{})).join();
//        stopWatch.stop();
//        System.out.println(stopWatch.getLastTaskTimeMillis());
    }
}