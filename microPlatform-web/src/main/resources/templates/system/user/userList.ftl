<ol class="breadcrumb">
    <li><a href="#">Home</a></li>
    <li><a href="#">Library</a></li>
    <li class="active">Data</li>
</ol>
<div class="row">
    <div class="panel panel-default">
        <div class="panel-heading">查询内容</div>
        <div class="panel-body">
            <form class="form-horizontal" role="form">
                <div class="form-group">
                    <label class="col-sm-1 control-label" for="ds_host">主机名</label>

                    <div class="col-sm-3">
                        <input class="form-control" id="ds_host" type="text" placeholder="192.168.1.161"/>
                    </div>
                    <label class="col-sm-1 control-label" for="ds_name">归属机构：</label>

                    <div class="col-sm-3">
                        <input class="form-control" id="ds_name" type="text" placeholder="msh"/>
                    </div>
                    <label class="col-sm-1 control-label" for="ds_name">登录名：</label>

                    <div class="col-sm-3">
                        <input class="form-control" id="ds_name" type="text" placeholder="msh"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-1 control-label" for="ds_username">归属部门：</label>

                    <div class="col-sm-3">
                        <input class="form-control" id="ds_username" type="text" placeholder="root"/>
                    </div>
                    <label class="col-sm-1 control-label" for="ds_password">姓&nbsp;&nbsp;&nbsp;名：</label>

                    <div class="col-sm-3">
                        <input class="form-control" id="ds_password" type="password" placeholder="123456"/>
                    </div>


                    <div class="col-sm-4">
                        <button type="button" class="btn btn-primary">查询</button>
                    </div>
                </div>
            </form>

        </div>
    </div>
    <!-- /.col -->
</div>
<div class="row">
    <table class="table table-striped  table-bordered" id="searchTable">
        <thead>
        <tr>
            <th w_index="id">编号</th>
            <th w_index="companyName">所属公司</th>
            <th w_index="officeName">所属部门</th>
            <th w_index="name" w_sort="name">姓名</th>
            <th w_index="loginName" w_align="left">登录名</th>
            <th w_index="mobile" w_align="left">电话</th>
            <th w_index="email">邮箱地址</th>
            <th w_render="operate">操作</th>
        </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</div>
<script type="text/javascript">
    var gridObj;
    $(function () {
        gridObj = $.fn.bsgrid.init('searchTable', {
            url: '/sys/user/userList?${_csrf.parameterName}=${_csrf.token}',
            // autoLoad: false,
            pageSizeSelect: true,
            pagingLittleToolbar: true,
            pageSize: 10
        });

        $(':button').addClass('btn btn-mini');
    });

    function operate(record, rowIndex, colIndex, options) {
        return '<a href="#" onclick="alert(\'ID=' + gridObj.getRecordIndexValue(record, 'ID') + '\');">修改</a>';
    }
</script>
