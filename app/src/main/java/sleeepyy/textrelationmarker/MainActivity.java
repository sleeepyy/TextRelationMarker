package sleeepyy.textrelationmarker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createFiles();
        Button openFileButton = findViewById(R.id.open_file_button);
        openFileButton.setOnClickListener(new View.OnClickListener() {
            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                startActivityForResult(intent,1);
//            }

            public void onClick(View v) {

                Intent in = new Intent(MainActivity.this, ListFileActivity.class);
                startActivityForResult(in, 0);
            }
        });

        Button openUrlButton = findViewById(R.id.open_url_button);
        openUrlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditText editText = new EditText(MainActivity.this);
                AlertDialog.Builder inputDialog = new AlertDialog.Builder(MainActivity.this);
                inputDialog.setTitle("Input Text Url: ");
                inputDialog.setView(editText);
                inputDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String url = editText.getText().toString();
                        Toast.makeText(MainActivity.this, "Trying to open " + url, Toast.LENGTH_SHORT).show();

                        Intent in = new Intent(MainActivity.this, ViewFileActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("url", url);
                        in.putExtras(bundle);
                        startActivity(in);
                    }
                });
                inputDialog.show();
            }
        });


    }

    private void createFiles() {
        String[] fileNames = {"安倍晋三再次当选日本首相 第4次内阁于今夜启动.txt", "巴黎大师赛蒂姆险胜过关 8号种子吞完败出局.txt", "杜特尔特称愿与日加强关系 日媒：安倍露出满意表情.txt", "俄阿伊三国元首推动跨里海地区合作.txt"};
        String[] _texts = {"海外网11月1日电 日本第195次特别国会于当地时间11月1日召开，日本自民党总裁安倍晋三在当日下午举行的众参两院全体大会首相提名选举中被选为第98任首相，全体阁僚和党高层留任。1日晚间，安倍第4次内阁将正式启动，这是继1952年吉田茂担任首相以来，日本时隔65年将再次出现“第4次内阁”。由于修宪势力获得了远远超过修宪动议所需众院额定议席数的三分之二以上，包含宪法第9条在内的修宪问题将成为焦点。\n" +
                "\n" +
                "据日本时事通讯社报道，在1日下午举行的日本众参两院首相提名选举中，日本自民党总裁安倍晋三再次当选为日本第98任首相。第4次安倍内阁启动后党内四大要职将全部留任，全体阁僚也将继续任职。自民党的大岛理森再任众议院议长、立宪民主党的赤松广隆当选为副议长，自民党的古屋圭司当选为议院运营委员长。\n" +
                "\n" +
                "8月3日启动的第3届安倍政府第3次改组内阁已于1日上午全员辞职。安倍晋三在众参两院全体大会上被指名为首相后，将与公明党党首山口那津男在首相官邸举行会谈，就维持联立政权等进行会谈。之后官房长官菅义伟将公布阁僚名单，晚些时候皇宫将举行首相任命仪式和阁僚认证仪式。1日晚，首相官邸将召开记者见面会，安倍将说明新内阁的基本姿态以及国内外诸多课题的应对方针。记者见面会之后，安倍拟在今晚的首次内阁会议上指示编制2017年度补充预算案，旨在充实育儿支援措施以推进其招牌政策“育人革命”。\n" +
                "\n" +
                "围绕着特别国会会期问题，在1日上午的众院各派协议会上，朝野政党已达成一致，将特别国会会期延长至12月9日，时间长达39天。日本首相安倍晋三将在特别国会期间发表施政信念演讲，并接受各党质询。在野党计划在特别国会期间继续就森友学园与加计学园问题追究安倍的责任。此前，日本执政党将会期拟定为8天，即11月1日至8日，而在野党要求将会期延长至12月上旬，充分保证审议时间。\n" +
                "\n" +
                "对于选举之后的日本，国际社会最为关注的问题无疑是修宪前景。在新一届的日本国会中，自民、希望、维新各党对包括《宪法》第九条在内的修宪积极态度，公明党态度谨慎，立宪民主党则“反对修改第九条”，共产、社民两党也持守护第九条的立场。安倍今年5月发表修宪主张，称希望在宪法第9条中增加自卫队的内容，并给出了“2020年施行新宪法”的明确时间表。随着“修宪势力”可能在此次众议院选战后壮大，安倍领导的自民党正在考虑加速此进程。修改战后日本的“和平宪法”条款，即宣布日本“放弃战争”的宪法第9条，为日本将自卫队最终升级为军队、重获战争权利扫清道路。安倍欲使日本重获国家交战权和集体防卫权，复归“正常国家”地位。一旦失去“和平宪法”的束缚日本会走向何方，会对东亚局势造成什么样的冲击，中韩等国对此担忧加剧。\n" +
                "\n" +
                "据此前报道，31日，日本首相安倍晋三（自民党总裁）在该党高层会议上正式宣布，11月1日第四届安倍内阁启动后党内四大要职将全部留任。安倍在会上呼吁团结称，“不辜负国民的托付，一项一项切实兑现选举承诺十分重要。”四大要职以外的党高层除了议员退职和众院选举落选者外都将继续任用。关于修宪，自民党把最快于明年由国会提议纳入考虑。以在《宪法》第九条中写明自卫队存在为主的该党修宪草案拟最快在明年初提交给例行国会，以便朝野各党展开讨论。（编译/海外网 巩浩）",
        "北京时间11月1日，2017赛季最后一项ATP大师赛、总奖金为450.7万欧元的劳力士巴黎大师赛展开了男单第二轮的争夺。赛会五号种子、奥地利人蒂姆在第二盘连续浪费三个赛点，才以6-4/6-7（3）/6-4险胜德国人高约维茨克；八号种子、西班牙人布斯塔4-6/1-6不敌法国老将马胡，晋级总决赛形势堪忧。\n" +
                "\n" +
                "蒂姆本来在开赛前宣布退赛专心备战总决赛，但是又意外的出现在巴黎大师赛签表中。奥地利人近来状态惨淡，美网之后的四站赛事仅仅拿到了一场胜利。高约维茨克世界排名第62位，德国人此前在法国梅兹拿下职业生涯首冠。两人此前两次交手各胜一场，最近一次是去年梅兹蒂姆两盘胜出。\n" +
                "\n" +
                "蒂姆在第三局浪费了两个破发点，他在第五局再度发起攻势破发得手。两人此后都没有送出破发点，五号种子以6-4先声夺人。第二盘鏖战至5-5平后，蒂姆在第11局完成关键破发。第12局他40-0手握三个赛点，却意外的连丢五分被对手回破带入抢七。高约维茨克手感正佳，抢七局一路领先7-3扳平。\n" +
                "\n" +
                "进入决胜盘，两人的发球局都表现的非常稳定，前八局没有送出破发点战至四平。第十局蒂姆抓住整盘唯一的破发机会，破发成功6-4拿到这场来之不易的胜利。全场比赛耗时126分钟，他在第三轮将迎战西班牙老将沃达斯科和美网亚军安德森之间的胜者。\n" +
                "\n" +
                "布斯塔目前世界排名第11位，这位美网打入四强的新面孔也是近况不佳，美网后总战绩为1胜3负。目前他的冠军积分在活跃球员中排在第八位，身后的安德森、波特罗等人来势汹汹入围形势并不明朗。马胡世界排名第111位，两人在今年美网有过交手，布斯塔直落三盘胜出。\n" +
                "\n" +
                "布斯塔开局之后取得优势，第三局破发之后保发3-1领先。马胡在第五局挽救破发点保发之后，第六局回破追平。第十局布斯塔虽然化解两个破发点追至平分，还是被对手破发4-6交出首盘胜利。第二盘马胡一上来就两次破发5-0遥遥领先，此后两人各自保发法国人以6-1击败对手晋级。他在第三轮的对手是塞尔维亚人克拉吉诺维奇，后者以6-4/6-4爆冷淘汰了十号种子、美国大炮奎雷伊。",
                "参考消息网11月1日报道 日媒称，菲律宾总统杜特尔特31日前往东京皇宫，拜会日本明仁天皇和皇后美智子，并讨论了两国间时而因历史问题而紧张的双边关系。\n" +
                        "\n" +
                        "据共同社10月31日报道，根据日本宫内厅发布的消息，这次会晤进行了约25分钟。在此期间，明仁天皇指出，在二战时期日本占领菲律宾的3年多时间里，有很多当地人失去了生命。\n" +
                        "\n" +
                        "杜特尔特说，两国已经跨越了历史，建立了合作关系，他还感谢日本在战后一直对菲提供援助。\n" +
                        "\n" +
                        "一位政府消息人士说，杜特尔特曾发表的一些争议性言论令一些日本政府人士对安排他与天皇夫妇会面感到担忧。但宫内厅说，杜特尔特对日本皇室怀有敬意，他在会见刚开始时显得有些紧张，在天皇夫妇现身后，他向他们鞠躬致意。\n" +
                        "\n" +
                        "报道称，日本首相安倍晋三30日与杜特尔特举行会谈，双方重申将在包括朝鲜问题等的地区问题上合作。双方还发表了关于日本今后5年对菲提供发展援助的共同声明。\n" +
                        "\n" +
                        "另据日本《产经新闻》10月31日报道，日菲两国首脑会谈30日举行。杜特尔特总统表达了与日本加强关系的愿望，称“我们的伙伴关系可以说是经得起考验的伙伴关系……菲律宾已做好与日本共同开创战略伙伴关系黄金时代的准备”。\n" +
                        "\n" +
                        "报道称，听到杜特尔特的一席发言，坐在对面的安倍首相不由得露出满意的表情。日本方面也鲜明地表现出对菲予以支持的姿态。\n" +
                        "\n" +
                        "报道称，其中日本重视的是提高海上保安机构的沿岸监视能力。日本政府将把有偿租借给菲律宾政府的两架海上自卫队TC-90教练机改为无偿转让，目的为使菲律宾“牵制中国”。\n" +
                        "\n" +
                        "报道称，杜特尔特在南海问题上并不是一边倒地指责中国。菲律宾也从中国获得了援助。杜特尔特去年上任后，在外交上表现出八面玲珑的特点。但在国内，政府军与伊斯兰激进派在棉兰老岛发生冲突，杜特尔特重点推进的“禁毒”政策陷入僵局，被迫调整路线。\n" +
                        "\n" +
                        "报道称，在冲突中受损的马拉维市，地区重建是否成功将直接关系到人们对政府的信任，但重建费用十分高昂，在中美的援助不明确的情况下，杜特尔特对日本的期待日益高涨。",
                "中青在线巴库11月2日电（中国青年报·中青在线驻南高加索记者 黄庆）11月1日，应伊朗总统鲁哈尼邀请，俄罗斯总统普京和阿塞拜疆总统阿利耶夫相聚伊朗首都德黑兰。3国总统就跨里海地区的交通运输、能源和经济合作，以及中东地区形势、与恐怖主义作斗争等问题进行了磋商。据此间媒体报道，3国首脑会晤的主要议题之一就是推动“北南国际运输走廊”建设。\n" +
                        "\n" +
                        "    “北南国际运输走廊”是连接欧亚大陆南北方向的重要运输通道，它将以铁路、公路、水路多种联运的方式，把南亚、东南亚与北欧、中东欧国家连接在一起。这一项目是2002年5月在俄罗斯的倡议下由印度、伊朗等国发起建设的，这条运输通道全长约5000公里。俄罗斯、阿塞拜疆和伊朗3国均为里海沿岸国家，在连接南亚、东南亚到北欧的交通运输通道上起着关键性的作用。由于存在核问题，伊朗在区域合作中也受到重重制约。2015年7月中旬，随着伊朗核问题的解决和西方解除对伊朗的制裁，重启“北南国际运输走廊”项目再次被提上议事日程。\n" +
                        "\n" +
                        "    有分析指出，阿塞拜疆在促进跨里海地区合作和推动“北南国际运输走廊”建设中发挥了积极作用。正是在阿塞拜疆领导人倡议下，2016年8月，俄阿伊3国总统在巴库举行了首次三方会晤，3国领导人就建设“北南国际运输走廊”达成了共识。阿塞拜疆处于俄罗斯与伊朗之间，是“北南国际运输走廊”中里海沿岸路段的关键环节。近年来，阿塞拜疆不断进行经济转型，希望通过发展交通运输，在跨境货物运输中获得更多收益。为了推进“北南国际运输走廊”建设，阿塞拜疆加快本国境内铁路建设的同时，也加大了对伊朗铁路建设的投资。有资料显示，为了促进阿伊之间的铁路建设，阿塞拜疆政府向伊朗提供了6000万欧元贷款，并与伊朗达成了修建伊朗通向阿塞拜疆边界的阿斯塔拉路段和货运站的协议。\n" +
                        "\n" +
                        "    目前，从阿塞拜疆到伊朗边界的铁路已经建成通车，伊朗境内约3公里路段也将于2017年11月底完成。据阿塞拜疆铁路公司总裁古尔班诺夫透露，阿伊之间的阿斯塔拉铁路线将于今年内开通。阿斯塔拉路段建成后，标志着“北南国际运输走廊”铁路线路将正式开通。有专家分析认为，俄阿伊3国联手，加速“北南国际运输走廊”的建设，将会促进跨里海地区的经济发展。这条运输通道的开通将会迅速提升“北南国际运输走廊”的货运能力。预计在初始阶段，这条运输通道年货运量为600万吨，远景规划可达2000万吨。阿伊之间铁路贯通后，大大缩短货物由印度运往北欧的时间，降低运输成本，将对苏伊式运河的货运形成竞争态势，未来的“北南国际运输走廊”甚至会改变欧亚大陆货物运输格局。\n" +
                        "\n" +
                        "    值得注意的是，正是在两天前，连接阿塞拜疆、格鲁吉亚和土耳其的“巴库-第比利斯-卡尔斯铁路”（BTK）刚刚开通，这条横跨南高加索地区的铁路使欧亚大陆东西方向增添了一条新通道。现在，欧亚大陆南北方向上的交通运输合作又出现了新进展。有专家认为，阿塞拜疆处于欧亚大陆东-西、南-北交通运输大动脉的交叉点上。对于阿塞拜疆来说，BTK铁路和“北南国际运输走廊”具有重要的政治经济意义，进一步提升了阿塞拜疆在南高加索地区的地位和作用。这两条交通要道将使阿塞拜疆成为本地区最重要的一个国家。\n" +
                        "\n" +
                        "    据报道，俄阿伊3国元首第三次会晤将在俄罗斯举行。有分析认为，三方合作越来越成为本地区更容易被接受的一种合作方式。阿利耶夫总统在3国首脑会晤后向媒体发表声明时指出，“三方合作的方式对地区安全具有重要意义，这种成功的合作对地区的稳定起到了重要的作用”。3国之间通过高层互动，便于排除疑虑，提高互信，在互利互惠、加强经济合作的同时，也有利于维护、巩固地区安全和政治稳定。"

        };

        for(int i=0; i<fileNames.length; ++i) {
            String fileName = fileNames[i];
            String _text = _texts[i];
            try {
                FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                outputStream.write(_text.getBytes());
                outputStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        menu.removeItem(R.id.gb2312);
        menu.removeItem(R.id.utf8);
        menu.removeItem(R.id.adjust);
        menu.removeItem(R.id.mark);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                doAbout();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void doAbout() {
        Dialog dialog = new AlertDialog.Builder(MainActivity.this).setTitle(
                R.string.aboutTitle).setMessage(R.string.aboutInfo)
                .setPositiveButton(R.string.aboutOK,
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialoginterface, int i) {
                            }
                        }).create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

}
