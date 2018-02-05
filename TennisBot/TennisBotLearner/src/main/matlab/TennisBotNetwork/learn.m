function net = learn(train_in_path, train_out_path, output_dir)

    %load input data
    train_in = load(train_in_path);
    train_out = load(train_out_path);
    train_in = train_in';
    train_out = train_out';
    a = size(train_in);
    b = size(train_out);
    
    disp(a);
    disp(b);
  
    hiddenSizes = 30; %number of hidden layers and neurons in each layer, can be set as vector i.e. [50, 50]  
    
    net = feedforwardnet(hiddenSizes);
    
    net.divideParam.trainRatio = 0.6;
    net.divideParam.valRatio = 0.2;
    net.divideParam.testRatio = 0.2;
    
    net.trainParam.epochs = 100;
    net.trainParam.goal = 0;
    net.trainParam.max_fail = 8;
    net.trainParam.mem_reduc = 1;
    net.trainParam.min_grad = 1e-10;
    net.trainParam.mu = 0.001;
    net.trainParam.mu_dec = 0.1;
    net.trainParam.mu_inc = 10;
    net.trainParam.mu_max = 1e10;
    net.trainParam.show = 25;
    net.trainParam.showCommandLine = 0;
    net.trainParam.showWindow = 1;
    net.trainParam.time = inf;
    
    net = train(net, train_in, train_out);
    
    %per = perform(net, y, train_out);
    
    w1 = net.IW{1};
    b1 = net.b{1};
    w2 = net.LW{2};
    b2 = net.b{2};

    save(sprintf('%s/mlp_w1.dat',output_dir), 'w1', '-ascii');
    save(sprintf('%s/mlp_b1.dat',output_dir), 'b1', '-ascii');
    save(sprintf('%s/mlp_w2.dat',output_dir), 'w2', '-ascii');
    save(sprintf('%s/mlp_b2.dat',output_dir), 'b2', '-ascii');


end

