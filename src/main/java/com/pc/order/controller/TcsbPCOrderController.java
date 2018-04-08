package com.pc.order.controller;

import com.pc.enums.OrderMethodEnum;
import com.pc.enums.OrderStatusEnum;
import com.pc.order.service.TcsbPCOrderServiceI;
import com.pc.order.vo.*;
import com.tcsb.desk.entity.TcsbDeskEntity;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.foodmealfun.entity.TcsbFoodMealFunEntity;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.order.service.TcsbOrderServiceI;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;
import com.tcsb.suborder.entity.TcsbSubOrderEntity;
import com.tcsb.tcsborderparent.entity.TcsbOrderParentEntity;
import com.tcsb.tcsborderparent.service.TcsbOrderParentServiceI;
import com.tcsb.userorderitem.entity.TcsbUserOrderItemEntity;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.util.BigDecimalUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/pc/PCOrderController")
public class TcsbPCOrderController {

    @Autowired
    private TcsbPCOrderServiceI tcsbPCOrderService;
    @Autowired
    private TcsbOrderParentServiceI tcsbOrderParentService;

    @Autowired
    private TcsbOrderServiceI tcsbOrderService;


    /**
     * 根据orderParentId获取所有的菜品
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "getFoodByorderParentId")
    @ResponseBody
    public AjaxJsonApi getFoodByorderParentId(HttpServletRequest request, HttpServletResponse response, String orderParentId) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        try {
            if (orderParentId.trim() != null) {
                ajaxJson = tcsbPCOrderService.getFoodByorderParentId(orderParentId);
            } else {
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("参数为空值");
            }
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("系统异常");
            e.printStackTrace();
        }
        return ajaxJson;
    }

    /**
     * 修改用餐人数和桌数
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "updateDeskNumAndPeople")
    @ResponseBody
    public AjaxJsonApi updateDeskNumAndPeople(HttpServletRequest request, HttpServletResponse response, String orderParentId, String people, String deskNum) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        try {
            if (orderParentId.trim() != null) {
                TcsbOrderParentEntity orderParentEntity = tcsbPCOrderService.get(TcsbOrderParentEntity.class, orderParentId);
                if (deskNum != null) {
                    orderParentEntity.setDeskNum(Integer.valueOf(deskNum));
                }
                if (people != null) {
                    orderParentEntity.setPeople(Integer.valueOf(people));
                }
                tcsbPCOrderService.saveOrUpdate(orderParentEntity);
            } else {
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("参数为空值");
            }
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("系统异常");
            e.printStackTrace();
        }
        return ajaxJson;
    }

    /**
     * 根据日期查找订单信息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "getOrderByDate")
    @ResponseBody
    public AjaxJsonApi getOrderByDate(HttpServletRequest request, HttpServletResponse response, TcsbOrderEntity orderEntity, DataGrid dataGrid) {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        CriteriaQuery cq = new CriteriaQuery(TcsbOrderEntity.class, dataGrid);
        if (orderEntity.getShopId() == null) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("店铺id为空");
            return ajaxJsonApi;
        }

        cq.eq("shopId", orderEntity.getShopId());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String queryDate = request.getParameter("queryDate");
        Date date = null;
        Calendar cl = null;
        try {
            date = sdf.parse(queryDate);
            cl = Calendar.getInstance();
            cl.setTime(date);
            cl.add(Calendar.DATE, 1);

            cq.ge("createDate", new SimpleDateFormat("yyyy-MM-dd").parse(sdf.format(date.getTime())));
            cq.le("createDate", new SimpleDateFormat("yyyy-MM-dd").parse(sdf.format(cl.getTime())));
        } catch (ParseException e) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("日期格式解析错误");
            e.printStackTrace();
            return ajaxJsonApi;
        }
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        this.tcsbPCOrderService.getDataGridReturn(cq, true);
        List<TcsbOrderEntity> list = dataGrid.getResults();

        List<OrderMessageVO> outList = new ArrayList<>();
        try {
            for (TcsbOrderEntity entity : list) {
                OrderMessageVO vo = new OrderMessageVO();
                MyBeanUtils.copyBeanNotNull2Bean(entity, vo);
                TcsbOrderParentEntity orderParentEntity = tcsbPCOrderService.get(TcsbOrderParentEntity.class, entity.getOrderParentId());
                vo.setOrderStatus(orderParentEntity.getOrderStatus());
                vo.setPeople(orderParentEntity.getPeople());
                TcsbDeskEntity deskEntity = tcsbPCOrderService.get(TcsbDeskEntity.class, orderParentEntity.getDeskId());
                vo.setDeskName(deskEntity.getDeskName());
                vo.setMethodName(OrderMethodEnum.getMessage(entity.getMethod()));
                vo.setOrderStatusName(OrderStatusEnum.getMessage(orderParentEntity.getOrderStatus()));
                if (entity.getPayMethod().equals("0")) {
                    vo.setRealTotalPrice(entity.getOnlinePrice());
                } else if (entity.getPayMethod().equals("1")) {
                    vo.setRealTotalPrice(entity.getOfflinePrice());
                }
                outList.add(vo);
            }
            dataGrid.setResults(outList);
            ajaxJsonApi.setObj(dataGrid);
        } catch (Exception e) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("系统错误");
            e.printStackTrace();
        }

        return ajaxJsonApi;
    }


    @RequestMapping("/getAutoOrderParent")
    @ResponseBody
    public PcOrderVo getAutoOrderParent(String orderparentId) {

        PcOrderVo ordervo = new PcOrderVo();
        ordervo.setOrderparent(tcsbOrderParentService.get(TcsbOrderParentEntity.class, orderparentId));
        List<TcsbOrderEntity> orderList = tcsbOrderParentService.findByProperty(TcsbOrderEntity.class, "orderParentId", orderparentId);
        ordervo.setOrder(orderList);
        List<SubOrderVo> subOrderVoList = new ArrayList<>();
        for (TcsbOrderEntity tcsbOrderEntity : orderList) {
            String hql = "from TcsbSubOrderEntity where orderNo='" + tcsbOrderEntity.getId() + "' and shopId='" + tcsbOrderEntity.getShopId() + "' and orderIstake='N'";
            List<TcsbSubOrderEntity> subOrderList = tcsbOrderParentService.findByQueryString(hql);
            for (TcsbSubOrderEntity tcsbSubOrderEntity : subOrderList) {
                SubOrderVo subOrder = new SubOrderVo();
                subOrder.setSubOrder(tcsbSubOrderEntity);
                List<SubOrderItemVo> subOrderItemVoList = new ArrayList<>();
                List<TcsbUserOrderItemEntity> subOrderItemList = tcsbOrderParentService.findByProperty(TcsbUserOrderItemEntity.class, "orderId", tcsbSubOrderEntity.getId());
                for (TcsbUserOrderItemEntity tcsbUserOrderItemEntity : subOrderItemList) {
                    SubOrderItemVo subOrderItemVo = new SubOrderItemVo();
                    subOrderItemVo.setSubOrderItem(tcsbUserOrderItemEntity);
                    //TcsbFoodEntity food = tcsbOrderParentService.get(TcsbFoodEntity.class, tcsbUserOrderItemEntity.getFoodId());
                    List<FoodMealVo> foodMealList = new ArrayList<>();
                    if (tcsbUserOrderItemEntity.getIsSetMeal().equals("1")) {
                        List<TcsbFoodMealFunEntity> foodMealFunList = tcsbOrderParentService.findByProperty(TcsbFoodMealFunEntity.class, "parentId", tcsbUserOrderItemEntity.getFoodId());
                        for (TcsbFoodMealFunEntity tcsbFoodMealFunEntity : foodMealFunList) {
                            TcsbFoodEntity mealfood = tcsbOrderParentService.get(TcsbFoodEntity.class, tcsbFoodMealFunEntity.getFoodId());
                            FoodMealVo foodMeal = new FoodMealVo();
                            foodMeal.setFoodName(mealfood.getName());
                            foodMealList.add(foodMeal);
                        }
                        subOrderItemVo.setFoodMealVo(foodMealList);
                    }
                    subOrderItemVoList.add(subOrderItemVo);
                }
                subOrder.setSubOrderItemVo(subOrderItemVoList);
                subOrderVoList.add(subOrder);
            }
        }
        ordervo.setSubOrder(subOrderVoList);
        return ordervo;
    }


    @RequestMapping("/getHandOrderParent")
    @ResponseBody
    public List<HandPrintVo> getHandOrderParent(String orderparentId) {

        List<HandPrintVo> handPrintVoList = new ArrayList<>();

        //TcsbOrderParentEntity tcsbOrderParentEntity = tcsbOrderParentService.get(TcsbOrderParentEntity.class, orderparentId);
        List<TcsbOrderEntity> orderList = tcsbOrderParentService.findByProperty(TcsbOrderEntity.class, "orderParentId", orderparentId);
        //根据订单获取每个订单的订单项

        for (TcsbOrderEntity tcsbOrderEntity : orderList) {
            HandPrintVo handPrintVo = new HandPrintVo();
            handPrintVo.setOrder(tcsbOrderEntity);
            List<OrderItemVo> orderItemVoList = new ArrayList<>();

            List<TcsbOrderItemEntity> orderItemList = tcsbOrderParentService.findByProperty(TcsbOrderItemEntity.class, "orderId", tcsbOrderEntity.getId());
            for (TcsbOrderItemEntity tcsbOrderItemEntity : orderItemList) {
                OrderItemVo orderItem = new OrderItemVo();
                orderItem.setOrderItem(tcsbOrderItemEntity);
                //TcsbFoodEntity food = tcsbOrderParentService.get(TcsbFoodEntity.class, tcsbOrderItemEntity.getFoodId());
                List<FoodMealVo> foodMealList = new ArrayList<>();
                if (tcsbOrderItemEntity.getIsSetMeal().equals("1")) {
                    List<TcsbFoodMealFunEntity> foodMealFunList = tcsbOrderParentService.findByProperty(TcsbFoodMealFunEntity.class, "parentId", tcsbOrderItemEntity.getFoodId());
                    for (TcsbFoodMealFunEntity tcsbFoodMealFunEntity : foodMealFunList) {
                        TcsbFoodEntity mealfood = tcsbOrderParentService.get(TcsbFoodEntity.class, tcsbFoodMealFunEntity.getFoodId());
                        FoodMealVo foodMeal = new FoodMealVo();
                        foodMeal.setFoodName(mealfood.getName());
                        foodMealList.add(foodMeal);
                    }
                    //subOrderItemVo.setFoodMealVo(foodMealList);
                }
                orderItem.setFoodMealVo(foodMealList);
                orderItemVoList.add(orderItem);
            }

            handPrintVo.setOrderItemList(orderItemVoList);
            handPrintVoList.add(handPrintVo);
        }

        return handPrintVoList;
    }


    @RequestMapping("/getSettleOrderItem")
    @ResponseBody
    public List<OrderItemVo> getSettleOrderItem(String orderparentId) {

        //List<HandPrintVo> handPrintVoList = new ArrayList<>();

        //TcsbOrderParentEntity tcsbOrderParentEntity = tcsbOrderParentService.get(TcsbOrderParentEntity.class, orderparentId);
        List<TcsbOrderEntity> orderList = tcsbOrderParentService.findByProperty(TcsbOrderEntity.class, "orderParentId", orderparentId);
        //根据订单获取每个订单的订单项
        List<OrderItemVo> orderItemVoList = new ArrayList<>();
        for (TcsbOrderEntity tcsbOrderEntity : orderList) {
            List<TcsbOrderItemEntity> orderItemList = tcsbOrderParentService.findByProperty(TcsbOrderItemEntity.class, "orderId", tcsbOrderEntity.getId());
            for (TcsbOrderItemEntity tcsbOrderItemEntity : orderItemList) {
                OrderItemVo orderItem = new OrderItemVo();
                orderItem.setOrderItem(tcsbOrderItemEntity);
                //TcsbFoodEntity food = tcsbOrderParentService.get(TcsbFoodEntity.class, tcsbOrderItemEntity.getFoodId());
                List<FoodMealVo> foodMealList = new ArrayList<>();
                if (StringUtil.isNotEmpty(tcsbOrderItemEntity.getIsSetMeal())) {
                    if (tcsbOrderItemEntity.getIsSetMeal().equals("1")) {
                        List<TcsbFoodMealFunEntity> foodMealFunList = tcsbOrderParentService.findByProperty(TcsbFoodMealFunEntity.class, "parentId", tcsbOrderItemEntity.getFoodId());
                        for (TcsbFoodMealFunEntity tcsbFoodMealFunEntity : foodMealFunList) {
                            TcsbFoodEntity mealfood = tcsbOrderParentService.get(TcsbFoodEntity.class, tcsbFoodMealFunEntity.getFoodId());
                            FoodMealVo foodMeal = new FoodMealVo();
                            foodMeal.setFoodName(mealfood.getName());
                            foodMealList.add(foodMeal);
                        }
                    }
                    orderItem.setFoodMealVo(foodMealList);
                }

                orderItemVoList.add(orderItem);
            }
        }

        return orderItemVoList;
    }

    @RequestMapping("/getFoodMealList")
    @ResponseBody
    public List<FoodMealVo> getFoodMealList(String foodid) {
        List<FoodMealVo> foodMealList = new ArrayList<>();
        List<TcsbFoodMealFunEntity> foodMealFunList = tcsbOrderParentService.findByProperty(TcsbFoodMealFunEntity.class, "parentId", foodid);
        for (TcsbFoodMealFunEntity tcsbFoodMealFunEntity : foodMealFunList) {
            TcsbFoodEntity mealfood = tcsbOrderParentService.get(TcsbFoodEntity.class, tcsbFoodMealFunEntity.getFoodId());
            FoodMealVo foodMeal = new FoodMealVo();
            foodMeal.setFoodName(mealfood.getName());
            foodMealList.add(foodMeal);
        }
        return foodMealList;
    }

    /**
     * 清台
     *
     * @return
     */
    @RequestMapping(params = "PCClearDesk")
    @ResponseBody
    public AjaxJsonApi clearDesk(String orderParentId) {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        try {
            ajaxJsonApi = tcsbPCOrderService.clearDesk(orderParentId);
        } catch (Exception e) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("系统异常");
            e.printStackTrace();
        }
        return ajaxJsonApi;
    }

    /**
     * pc端更新订单价格
     *
     * @return
     * @throws ParseException
     * @throws Exception
     */
    @RequestMapping(params = "updateOrderPrice")
    @ResponseBody
    public AjaxJsonApi updateOrderPrice(HttpServletRequest request,
                                        @RequestParam("orderParentId") String orderParentId)
            throws ParseException {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();

        double totalPrice = 0.0;
        TcsbOrderParentEntity orderParentEntity = tcsbOrderService.get(TcsbOrderParentEntity.class, orderParentId);
        if (orderParentEntity != null) {
            if (orderParentEntity.getOrderStatus().equals("1")) {
                List<TcsbOrderEntity> orderEntities = tcsbOrderService.findByProperty(TcsbOrderEntity.class, "orderParentId", orderParentId);
                for (TcsbOrderEntity orderEntity : orderEntities) {
                    if (orderEntity.getPayStatus().equals("0")) {
                        List<TcsbOrderItemEntity> tcsbOrderItemEntity = tcsbOrderService
                                .findByProperty(TcsbOrderItemEntity.class, "orderId",
                                        orderEntity.getId());

                        for (TcsbOrderItemEntity orderItemEntity : tcsbOrderItemEntity) {

                            totalPrice = BigDecimalUtil.add(totalPrice, orderItemEntity.getPrice() * (orderItemEntity.getCount() - (orderItemEntity.getRetreatNum() == null ? 0.0 : orderItemEntity.getRetreatNum())));
                        }
                        orderEntity.setTotalPrice(totalPrice);
                        tcsbOrderService.saveOrUpdate(orderEntity);
                    }
                }
            } else {
                ajaxJsonApi.setSuccess(false);
                ajaxJsonApi.setMsg("订单未处于使用中");
            }
        } else {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("总订单号不存在");
        }


        return ajaxJsonApi;
    }

}
