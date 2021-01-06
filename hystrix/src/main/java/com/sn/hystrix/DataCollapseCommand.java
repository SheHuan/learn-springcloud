package com.sn.hystrix;

import com.netflix.hystrix.HystrixCollapser;
import com.netflix.hystrix.HystrixCollapserKey;
import com.netflix.hystrix.HystrixCollapserProperties;
import com.netflix.hystrix.HystrixCommand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 请求合并的的处理
 * <p>
 * 第一个泛型：批处理返回的数据类型
 * 第二个泛型：返回值类型
 * 第三个泛型：请求参数类型
 */
public class DataCollapseCommand extends HystrixCollapser<List<String>, String, Integer> {
    private DataBatchService dataBatchService;
    private Integer id;

    public DataCollapseCommand(DataBatchService dataBatchService, Integer id) {
        // 间隔500毫秒内的请求会被合并，最多合并10个请求
        super(HystrixCollapser.Setter.withCollapserKey(HystrixCollapserKey.Factory.asKey("collapse"))
                .andCollapserPropertiesDefaults(HystrixCollapserProperties.Setter()
                        .withTimerDelayInMilliseconds(500)
                        .withMaxRequestsInBatch(10)));
        this.dataBatchService = dataBatchService;
        this.id = id;
    }

    /**
     * 单个请求的参数
     *
     * @return
     */
    @Override
    public Integer getRequestArgument() {
        return id;
    }

    /**
     * 请求合并的方法
     *
     * @param collapsedRequests
     * @return
     */
    @Override
    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, Integer>> collapsedRequests) {
        ArrayList<Integer> ids = new ArrayList<>(collapsedRequests.size());
        for (CollapsedRequest<String, Integer> collapsedRequest : collapsedRequests) {
            ids.add(collapsedRequest.getArgument());
        }
        // 将合并好的请求数据统一发送
        return new DataBatchCommand(ids, dataBatchService);
    }

    /**
     * 请求结果分发
     *
     * @param batchResponse
     * @param collapsedRequests
     */
    @Override
    protected void mapResponseToRequests(List<String> batchResponse, Collection<CollapsedRequest<String, Integer>> collapsedRequests) {
        int index = 0;
        for (CollapsedRequest<String, Integer> collapsedRequest : collapsedRequests) {
            collapsedRequest.setResponse(batchResponse.get(index++));
        }
    }
}
