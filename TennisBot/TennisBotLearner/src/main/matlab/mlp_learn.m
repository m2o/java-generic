function net=mlp_learn(train_in_path,train_out_path,validate_in_path,validate_out_path,test_in_path,test_out_path,output_dir)

    train_in = load(train_in_path);
    train_out = load(train_out_path);
    validate_in = load(validate_in_path);
    validate_out = load(validate_out_path);
    test_in = load(test_in_path);
    test_out = load(test_out_path);

    train_in = train_in';
    train_out = train_out';
    validate_in = validate_in';
    validate_out = validate_out';
    test_in = test_in';
    test_out = test_out';
   
    train_validate_in = [train_in validate_in];
    train_validate_out = [train_out validate_out];
    
    size(train_validate_in)
    size(train_validate_out)

    %datacursormode on
    %
    %figure
    %hist(train_coefs(:,1),[1:0.05:2])
    %title('training set')
    %
    %figure
    %hist(validate_coefs(:,1),[1:0.05:2])
    %title('validate set')
    %
    %figure
    %hist(test_coefs(:,1),[1:0.05:2])
    %title('test set')

    hidden_layers = [100];

    net = newff(train_validate_in, train_validate_out, hidden_layers, {'logsig', 'logsig'}, 'trainscg', 'learngdm','mse',{},{})
    %trainbr
    net.initFcn = 'initlay';
    %net.layers{1}.initFcn = 'initnw';
    %net.layers{2}.initFcn = 'initnw';
    net.layers{1}.initFcn = 'initwb';
    net.layers{2}.initFcn = 'initwb';
    net.inputWeights{1,1}.initFcn='rands';
    net.layerWeights{2,1}.initFcn='rands';
    net.biases{1}.initFcn='rands';
    net.biases{2}.initFcn='rands';

    net.divideFcn = 'dividerand';
    net.divideParam.trainRatio = 0.6;
    net.divideParam.valRatio = 0.2;
    net.divideParam.testRatio = 0.2;

    %net.divideFcn = 'divideblock';
    %net.divideParam.trainRatio = size(train_in,2)/size(train_validate_in,2)
    %net.divideParam.valRatio = 1-net.divideParam.trainRatio
    %net.divideParam.testRatio = 0;

    %net.divideFcn = '';

    
    net.trainParam.goal = 0;
    net.trainParam.time = inf;
    net.trainParam.showWindow = 1;
    net.trainParam.showCommandLine = 1;
    net.trainParam.show = 10;
    net.trainParam.max_fail = 8;
    net.trainParam.mu_max = 10e10;
    net.efficiency.memoryReduction = 1;
    net.trainParam.min_grad = 1e-10;
    net.trainParam.epochs = 1000;

    net = init(net);
    [net,tr] = train(net, train_validate_in, train_validate_out);
    %trainscg
    plotperf(tr)

    %simpleclassOutputsTrain = sim(net, train_in);
    %simpleclassOutputsValidate = sim(net, validate_in);
    %simpleclassOutputsTest = sim(net, test_in);

    %mse_train = mse(train_out-simpleclassOutputsTrain);
    %mse_validate = mse(validate_out-simpleclassOutputsValidate);
    %mse_test = mse(test_out-simpleclassOutputsTest);

    %plotconfusion(train_out, simpleclassOutputsTrain,'training set',validate_out, simpleclassOutputsValidate,'validation set',test_out, simpleclassOutputsTest,'testing set');

    w1 = net.IW{1};
    b1 = net.b{1};
    w2 = net.LW{2};
    b2 = net.b{2};

    save(sprintf('%s/mlp_w1.dat',output_dir), 'w1', '-ascii');
    save(sprintf('%s/mlp_b1.dat',output_dir), 'b1', '-ascii');
    save(sprintf('%s/mlp_w2.dat',output_dir), 'w2', '-ascii');
    save(sprintf('%s/mlp_b2.dat',output_dir), 'b2', '-ascii');
end