import React,{useRef,useEffect,useState} from 'react' ;
import {Modal,Button} from 'antd' ;
import Print from 'react-print-html'
import {getJypzById,saveTozbz} from '@/services/lqbz/jygl'
import { ExclamationCircleOutlined } from '@ant-design/icons';

const PrintModal = (props) =>{

  const {printModalVisible,onCancel,okHandle,jyId} = props;
  const [jypz,handleJypz] =useState({}) ;
  const [confirmMess,handleConfirmMess] =useState('') ;

  const printRef =useRef() ;

  useEffect(() =>{
    const lostData = async ()=>{
      const response = await getJypzById({id:jyId})
      return response ;
    }
    lostData().then((res) =>{
      if (res.result){
        handleJypz(res.result);
        if (res.result.isbr > 0){
          handleConfirmMess("充值数量大于结存数，是否继续打印，打印后，联勤保障中的结存将为负数");
        }
      }
    })
  }, [jyId])

  function handleProps() {
    // document.querySelector('#print').style.display="block"
    Print(printRef.current)
    onCancel()
    saveTozbz({id:jyId}) ;
  }

  function confirm() {
    Modal.confirm({
      title: '是否打印？',
      icon: <ExclamationCircleOutlined />,
      content: `${confirmMess}`,
      okText: '确认',
      cancelText: '取消',
      onOk: ()=>handleProps(),
      onCancel: ()=>onCancel()
    });
  }

    return(
        <Modal
          destroyOnClose
          visible={printModalVisible}
          width={800}
          bodyStyle={{height: 500}}
          closable={false}
          okButtonProps={{ hidden: true }}
          cancelButtonProps={{ hidden: true }}
        >

          <Button onClick={confirm} type="primary" style={{ marginBottom: 20 }}>
            打印
          </Button>

          <div id="print" ref={printRef} style={{textAlign: 'center',width: '100%'}} >
            <div style={{fontSize: '25px',fontWeight: 'bolder' ,width: '100%'}}><h1>油料供应充值凭证</h1></div>
            <div style={{width: '100%'}}>
            <table  cellspacing= '0' border='1px' width="100%" >
              <tbody >
                <tr >
                  <td width='14.28%' >用油单位</td>
                  <td width='42.84%' colSpan="3">{jypz.yongyoudanwei}</td>
                  <td width='14.28%'>充值时间</td>
                  <td width='28.56%' colSpan="2">{jypz.chongzhisj}</td>
                </tr>
                <tr >
                  <td width='14.28%' rowSpan={4}>充值明细</td>
                  <td width='14.28%' rowSpan={2}>车牌号</td>
                  <td width='14.28%' rowSpan={2}>油品</td>
                  <td width='14.28%' rowSpan={2}>数量（公斤）</td>
                  <td width='14.28%' rowSpan={2}>经办人</td>
                  <td width='28.56%' colSpan="2">仪表显示公里（公里）</td>
                </tr>
                <tr>

                  <td width='14.28%' >上次</td>
                  <td width='14.28%' >本次</td>
                </tr>
                <tr height={30}>
                  <td width='14.28%'>{jypz.numberplate}</td>
                  <td width='14.28%'>{jypz.youpin}</td>
                  <td width='14.28%'>{jypz.shuliang}</td>
                  <td width='14.28%'>{jypz.jingbanren}</td>
                  <td width='14.28%'>{jypz.shangciyibiaoshu}</td>
                  <td width='14.28%'>{jypz.benciyibiaoshu}</td>
                </tr>
                <tr height={30}>
                  <td width='14.28%'></td>
                  <td width='14.28%'></td>
                  <td width='14.28%'></td>
                  <td width='14.28%'></td>
                  <td width='14.28%'></td>
                  <td width='14.28%'></td>
                </tr>
              </tbody>
            </table>
            </div>
          </div>
        </Modal>
)
}

export default PrintModal ;
