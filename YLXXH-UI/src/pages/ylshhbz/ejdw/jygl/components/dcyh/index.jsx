import React ,{useState,useEffect} from 'react' ;
import {Modal,message} from 'antd' ;
import {getDcyhxxBycarnum} from '@/services/ylshh/dckp'

const DcyhkpModadl = (props) =>{
  const { modalDckpVisible, onCancel,numberplate ,chongzhisj } = props;

  const [carInfo,handleInfo] =useState({}) ;
  const [carInfoList,handleInfoList] =useState([]) ;
  useEffect(()=>{
    const lostData =async () =>{
      const response = await getDcyhxxBycarnum({chongzhisj,numberplate});
      if (response.result){
        return response.result
      }
    }

    lostData().then((res)=>{
      console.info(res)
      handleInfo(res.carInfo) ;
      handleInfoList(res.carInfoList);
      if(res.car === null || res.car === undefined){
        message.error("车辆信息不存在,请录入车辆信息")
      }
    })
  },[numberplate])
  return  (
    <Modal
    destroyOnClose
    visible={modalDckpVisible}
    okButtonProps={false}
    onCancel={onCancel}
    width={1000}
  >
      <div style={{textAlign: 'center',width: '100%'}}>
        <div style={{fontSize: '25px',fontWeight: 'bolder' ,width: '100%'}}><h1>油料社会化保障车辆单车油耗考核卡片</h1></div>
        <div style={{width: '100%'}}>
          <table cellSpacing= '0' border='1px' width="100%">
            <tbody>
              <tr>
                <td width='11.11%'>用油单位</td>
                <td width='33.33%' colSpan={3}>{carInfo==null ? "" :carInfo.yingyoudanwei}</td>
                <td width='11.11%'>车牌号</td>
                <td width='11.11%'>{carInfo ==null? "" :carInfo.numberplate}</td>
                <td width='11.11%'>油品</td>
                <td width='22.22%' colSpan={2}>{carInfo ==null? "" :carInfo.youpin}</td>
              </tr>
              <tr>
                <td width='11.11%'>类型</td>
                <td width='11.11%'>{carInfo ==null? "" :carInfo.leixing}</td>
                <td width='11.11%'>品牌</td>
                <td width='11.11%'>{carInfo ==null? "" :carInfo.pinpai}</td>
                <td width='11.11%'>排量</td>
                <td width='11.11%'>{carInfo ==null? "" :carInfo.pailiang}</td>
                <td width='11.11%'>型号</td>
                <td width='22.22%' colSpan={2}>{carInfo ==null? "" :carInfo.xinghao}</td>
              </tr>
              <tr>
                <td width='11.11%'>月份</td>
                <td width='11.11%'>消耗标准（升/百公里）</td>
                <td width='11.11%'>执行标准（升/百公里）</td>
                <td width='11.11%'>里程初读数（公里）</td>
                <td width='11.11%'>里程末读数（公里）</td>
                <td width='11.11%'>行驶里程（公里）</td>
                <td width='11.11%'>摩托小时（小时）</td>
                <td width='11.11%'>耗油量（升）</td>
                <td width='11.11%'>确认签字</td>
              </tr>
              {carInfoList ?carInfoList.map(v=>{
                return (
                  <tr>
                    <td width='11.11%'>{v.yuefen}月</td>
                    <td width='11.11%'>{v.youhaobiaozhun}</td>
                    <td width='11.11%'>{v.zhixingbiaozhun}</td>
                    <td width='11.11%'>{v.lichengchudu}</td>
                    <td width='11.11%'>{v.lichengweidu}</td>
                    <td width='11.11%'>{v.xingshilicheng}</td>
                    <td width='11.11%'>{v.motuoxiaoshi}</td>
                    <td width='11.11%'>{v.haoyouliang}</td>
                    <td width='11.11%'>{v.qianziname}</td>
                  </tr>
                )
              }) : null}


            </tbody>
          </table>
        </div>
      </div>

    </Modal>
    )
}
export default DcyhkpModadl ;
