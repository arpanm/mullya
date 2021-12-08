import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities, reset } from './payment-details.reducer';
import { IPaymentDetails } from 'app/shared/model/payment-details.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PaymentDetails = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const paymentDetailsList = useAppSelector(state => state.paymentDetails.entities);
  const loading = useAppSelector(state => state.paymentDetails.loading);
  const totalItems = useAppSelector(state => state.paymentDetails.totalItems);
  const links = useAppSelector(state => state.paymentDetails.links);
  const entity = useAppSelector(state => state.paymentDetails.entity);
  const updateSuccess = useAppSelector(state => state.paymentDetails.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const { match } = props;

  return (
    <div>
      <h2 id="payment-details-heading" data-cy="PaymentDetailsHeading">
        <Translate contentKey="mullyaApp.paymentDetails.home.title">Payment Details</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="mullyaApp.paymentDetails.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="mullyaApp.paymentDetails.home.createLabel">Create new Payment Details</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          pageStart={paginationState.activePage}
          loadMore={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
          threshold={0}
          initialLoad={false}
        >
          {paymentDetailsList && paymentDetailsList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="mullyaApp.paymentDetails.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('amount')}>
                    <Translate contentKey="mullyaApp.paymentDetails.amount">Amount</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('paymentType')}>
                    <Translate contentKey="mullyaApp.paymentDetails.paymentType">Payment Type</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('onlinePgType')}>
                    <Translate contentKey="mullyaApp.paymentDetails.onlinePgType">Online Pg Type</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('pgTxnId')}>
                    <Translate contentKey="mullyaApp.paymentDetails.pgTxnId">Pg Txn Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('pgStatus')}>
                    <Translate contentKey="mullyaApp.paymentDetails.pgStatus">Pg Status</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('offlineTxnId')}>
                    <Translate contentKey="mullyaApp.paymentDetails.offlineTxnId">Offline Txn Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('offlineTxnDetails')}>
                    <Translate contentKey="mullyaApp.paymentDetails.offlineTxnDetails">Offline Txn Details</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('offlineTxnCollectedBy')}>
                    <Translate contentKey="mullyaApp.paymentDetails.offlineTxnCollectedBy">Offline Txn Collected By</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('offlineTxnClearingStatus')}>
                    <Translate contentKey="mullyaApp.paymentDetails.offlineTxnClearingStatus">Offline Txn Clearing Status</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('paymentDate')}>
                    <Translate contentKey="mullyaApp.paymentDetails.paymentDate">Payment Date</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('paymentInitTime')}>
                    <Translate contentKey="mullyaApp.paymentDetails.paymentInitTime">Payment Init Time</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('paymentUpdateTime')}>
                    <Translate contentKey="mullyaApp.paymentDetails.paymentUpdateTime">Payment Update Time</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('paymentStatus')}>
                    <Translate contentKey="mullyaApp.paymentDetails.paymentStatus">Payment Status</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createdOn')}>
                    <Translate contentKey="mullyaApp.paymentDetails.createdOn">Created On</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createdBy')}>
                    <Translate contentKey="mullyaApp.paymentDetails.createdBy">Created By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedOn')}>
                    <Translate contentKey="mullyaApp.paymentDetails.updatedOn">Updated On</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="mullyaApp.paymentDetails.updatedBy">Updated By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="mullyaApp.paymentDetails.order">Order</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {paymentDetailsList.map((paymentDetails, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`${match.url}/${paymentDetails.id}`} color="link" size="sm">
                        {paymentDetails.id}
                      </Button>
                    </td>
                    <td>{paymentDetails.amount}</td>
                    <td>
                      <Translate contentKey={`mullyaApp.PaymentType.${paymentDetails.paymentType}`} />
                    </td>
                    <td>
                      <Translate contentKey={`mullyaApp.PGType.${paymentDetails.onlinePgType}`} />
                    </td>
                    <td>{paymentDetails.pgTxnId}</td>
                    <td>{paymentDetails.pgStatus}</td>
                    <td>{paymentDetails.offlineTxnId}</td>
                    <td>{paymentDetails.offlineTxnDetails}</td>
                    <td>{paymentDetails.offlineTxnCollectedBy}</td>
                    <td>{paymentDetails.offlineTxnClearingStatus}</td>
                    <td>{paymentDetails.paymentDate}</td>
                    <td>
                      {paymentDetails.paymentInitTime ? (
                        <TextFormat type="date" value={paymentDetails.paymentInitTime} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {paymentDetails.paymentUpdateTime ? (
                        <TextFormat type="date" value={paymentDetails.paymentUpdateTime} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      <Translate contentKey={`mullyaApp.PaymentStatus.${paymentDetails.paymentStatus}`} />
                    </td>
                    <td>
                      {paymentDetails.createdOn ? (
                        <TextFormat type="date" value={paymentDetails.createdOn} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{paymentDetails.createdBy}</td>
                    <td>
                      {paymentDetails.updatedOn ? (
                        <TextFormat type="date" value={paymentDetails.updatedOn} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{paymentDetails.updatedBy}</td>
                    <td>{paymentDetails.order ? <Link to={`order/${paymentDetails.order.id}`}>{paymentDetails.order.id}</Link> : ''}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${paymentDetails.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`${match.url}/${paymentDetails.id}/edit`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`${match.url}/${paymentDetails.id}/delete`}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="mullyaApp.paymentDetails.home.notFound">No Payment Details found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default PaymentDetails;
