package com.sn.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

import java.util.List;

/**
 * 批量发起请求
 */
public class DataBatchCommand extends HystrixCommand<List<String>> {
    private List<Integer> ids;
    private DataBatchService dataBatchService;

    protected DataBatchCommand(List<Integer> ids, DataBatchService dataBatchService) {
        super(HystrixCommand.Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("batch")));
        this.ids = ids;
        this.dataBatchService = dataBatchService;
    }

    @Override
    protected List<String> run() throws Exception {
        return dataBatchService.getDataByIds(ids);
    }
}
