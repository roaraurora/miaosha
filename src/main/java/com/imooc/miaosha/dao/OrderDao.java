package com.imooc.miaosha.dao;

import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.OrderInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface OrderDao {

    @Select("select * from miaosha_order where user_id=#{userId} and goods_id=#{goodsId}")
    MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userId") long userId, @Param("goodsId") long goodsId);

    /* *
     *  select last_insert_id() : 执行的sql语句 返回上次插入的记录的自增主键
     *  keyProperty : 绑定到对应的domain对象上面的属性 orderInfo.id
     *  resultType : keyProperty的java类型
     *  before : 在@Insert之后执行
     *  keyColumn : ？？？
     *  int类型的返回值 : 插入影响到记录的行数
     *
     *  @doc:这个注解的功能与 <selectKey> 标签完全一致，用在已经被 @Insert 或 @InsertProvider 或 @Update 或 @UpdateProvider 注解了的方法上。
     *      若在未被上述四个注解的方法上作 @SelectKey 注解则视为无效。
     *      如果你指定了 @SelectKey 注解，那么 MyBatis 就会忽略掉由 @Options 注解所设置的生成主键或设置（configuration）属性。
     *      属性有：statement 填入将会被执行的 SQL 字符串数组，keyProperty 填入将会被更新的参数对象的属性的值，before 填入 true 或 false 以指明 SQL 语句应被在插入语句的之前还是之后执行。
     *      resultType 填入 keyProperty 的 Java 类型和用 Statement、 PreparedStatement 和 CallableStatement 中的 STATEMENT、 PREPARED 或 CALLABLE 中任一值填入 statementType。默认值是 PREPARED。
     *
     */
    @Insert("insert into order_info(user_id,goods_id,goods_name,goods_count,goods_price,order_channel,status,create_date) " +
            "values(#{userId}, #{goodsId},#{goodsName},#{goodsCount},#{goodsPrice},#{orderChannel},#{status},#{createDate})")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
    int insert(OrderInfo orderInfo);

    //userId goodsId orderId是从miaohsaOrder对象中获取出来的
    @Insert("insert into miaosha_order (user_id,goods_id,order_id) values(#{userId},#{goodsId},#{orderId})")
    void insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);
}
