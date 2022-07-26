package com.zhixiang.health.common.http.pagination;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * 分页信息
 * @author HeJiawang
 * @since 2020-04-21
 */
public class Page<T> implements IPage<T> {

    private static final long serialVersionUID = 8545996863226528798L;

    /**
     * 查询数据列表
     */
    private List<T> records = Collections.emptyList();

    /**
     * 总数
     */
    private long total = 0;
    /**
     * 每页显示条数，默认 10
     */
    private long pageSize = 10;

    /**
     * 当前页
     */
    private long current = 1;

    /**
     * 排序字段信息
     */
    private List<OrderItem> orders = new ArrayList<>();

    /**
     * 自动优化 COUNT SQL
     */
    private boolean optimizeCountSql = true;
    /**
     * 是否进行 count 查询
     */
    private boolean isSearchCount = true;
    /**
     * 是否命中count缓存
     */
    private boolean hitCount = false;

    public Page() {
    }

    /**
     * 分页构造函数
     *
     * @param current 当前页
     * @param pageSize    每页显示条数
     */
    public Page(long current, long pageSize) {
        this(current, pageSize, 0);
    }

    public Page(long current, long pageSize, long total) {
        this(current, pageSize, total, true);
    }

    public Page(long current, long pageSize, boolean isSearchCount) {
        this(current, pageSize, 0, isSearchCount);
    }

    public Page(long current, long pageSize, long total, boolean isSearchCount) {
        if (current > 1) {
            this.current = current;
        }
        this.pageSize = pageSize;
        this.total = total;
        this.isSearchCount = isSearchCount;
    }

    /**
     * 是否存在上一页
     *
     * @return true / false
     */
    public boolean hasPrevious() {
        return this.current > 1;
    }

    /**
     * 是否存在下一页
     *
     * @return true / false
     */
    public boolean hasNext() {
        return this.current < this.getPages();
    }

    @Override
    public List<T> getRecords() {
        return this.records;
    }

    @Override
    public Page<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @Override
    public long getTotal() {
        return this.total;
    }

    @Override
    public Page<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    public long getSize() {
        return this.pageSize;
    }

    @Override
    public Page<T> setSize(long pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    @Override
    public long getCurrent() {
        return this.current;
    }

    @Override
    public Page<T> setCurrent(long current) {
        this.current = current;
        return this;
    }

    /**
     * 获取当前正序排列的字段集合
     * <p>
     * 为了兼容，将在不久后废弃
     *
     * @return 正序排列的字段集合
     * @see #getOrders()
     * @deprecated 3.2.0
     */
    @Override
    @Deprecated
    public String[] ascs() {
        return CollectionUtils.isNotEmpty(orders) ? mapOrderToArray(OrderItem::isAsc) : null;
    }

    /**
     * 查找 order 中正序排序的字段数组
     *
     * @param filter 过滤器
     * @return 返回正序排列的字段数组
     */
    private String[] mapOrderToArray(Predicate<OrderItem> filter) {
        List<String> columns = new ArrayList<>(orders.size());
        orders.forEach(i -> {
            if (filter.test(i)) {
                columns.add(i.getColumn());
            }
        });
        return columns.toArray(new String[0]);
    }

    /**
     * 移除符合条件的条件
     *
     * @param filter 条件判断
     */
    private void removeOrder(Predicate<OrderItem> filter) {
        for (int i = orders.size() - 1; i >= 0; i--) {
            if (filter.test(orders.get(i))) {
                orders.remove(i);
            }
        }
    }

    /**
     * 添加新的排序条件，构造条件可以使用工厂
     *
     * @param items 条件
     * @return 返回分页参数本身
     */
    public Page<T> addOrder(OrderItem... items) {
        orders.addAll(Arrays.asList(items));
        return this;
    }

    /**
     * 添加新的排序条件，构造条件可以使用工厂
     *
     * @param items 条件
     * @return 返回分页参数本身
     */
    public Page<T> addOrder(List<OrderItem> items) {
        orders.addAll(items);
        return this;
    }

    /**
     * 设置需要进行正序排序的字段
     * <p>
     * Replaced:{@link #addOrder(OrderItem...)}
     *
     * @param ascs 字段
     * @return 返回自身
     * @deprecated 3.2.0
     */
    @Deprecated
    public Page<T> setAscs(List<String> ascs) {
        return CollectionUtils.isNotEmpty(ascs) ? setAsc(ascs.toArray(new String[0])) : this;
    }

    /**
     * 升序
     * <p>
     * Replaced:{@link #addOrder(OrderItem...)}
     *
     * @param ascs 多个升序字段
     * @deprecated 3.2.0
     */
    @Deprecated
    public Page<T> setAsc(String... ascs) {
        // 保证原来方法 set 的语意
        removeOrder(OrderItem::isAsc);
        for (String s : ascs) {
            addOrder(OrderItem.asc(s));
        }
        return this;
    }

    /**
     * 获取需简要倒序排列的字段数组
     * <p>
     *
     * @return 倒序排列的字段数组
     * @see #getOrders()
     * @deprecated 3.2.0
     */
    @Override
    @Deprecated
    public String[] descs() {
        return mapOrderToArray(i -> !i.isAsc());
    }

    /**
     * Replaced:{@link #addOrder(OrderItem...)}
     *
     * @param descs 需要倒序排列的字段
     * @return 自身
     * @deprecated 3.2.0
     */
    @Deprecated
    public Page<T> setDescs(List<String> descs) {
        // 保证原来方法 set 的语意
        if (CollectionUtils.isNotEmpty(descs)) {
            removeOrder(item -> !item.isAsc());
            for (String s : descs) {
                addOrder(OrderItem.desc(s));
            }
        }
        return this;
    }

    /**
     * 降序，这方法名不知道是谁起的
     * <p>
     * Replaced:{@link #addOrder(OrderItem...)}
     *
     * @param descs 多个降序字段
     * @deprecated 3.2.0
     */
    @Deprecated
    public Page<T> setDesc(String... descs) {
        setDescs(Arrays.asList(descs));
        return this;
    }

    @Override
    public List<OrderItem> orders() {
        return getOrders();
    }

    public List<OrderItem> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderItem> orders) {
        this.orders = orders;
    }

    @Override
    public boolean optimizeCountSql() {
        return optimizeCountSql;
    }

    @Override
    public boolean isSearchCount() {
        if (total < 0) {
            return false;
        }
        return isSearchCount;
    }

    public Page<T> setSearchCount(boolean isSearchCount) {
        this.isSearchCount = isSearchCount;
        return this;
    }

    public Page<T> setOptimizeCountSql(boolean optimizeCountSql) {
        this.optimizeCountSql = optimizeCountSql;
        return this;
    }

    @Override
    public void hitCount(boolean hit) {
        this.hitCount = hit;
    }

    @Override
    public boolean isHitCount() {
        return hitCount;
    }

    public long getPageSize() {
        return pageSize;
    }

    public void setPageSize(long pageSize) {
        this.pageSize = pageSize;
    }
}
