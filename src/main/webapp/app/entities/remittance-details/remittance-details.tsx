import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroller';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities, reset } from './remittance-details.reducer';
import { IRemittanceDetails } from 'app/shared/model/remittance-details.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const RemittanceDetails = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(props.location, ITEMS_PER_PAGE, 'id'), props.location.search)
  );
  const [sorting, setSorting] = useState(false);

  const remittanceDetailsList = useAppSelector(state => state.remittanceDetails.entities);
  const loading = useAppSelector(state => state.remittanceDetails.loading);
  const totalItems = useAppSelector(state => state.remittanceDetails.totalItems);
  const links = useAppSelector(state => state.remittanceDetails.links);
  const entity = useAppSelector(state => state.remittanceDetails.entity);
  const updateSuccess = useAppSelector(state => state.remittanceDetails.updateSuccess);

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
      <h2 id="remittance-details-heading" data-cy="RemittanceDetailsHeading">
        <Translate contentKey="mulyaaApp.remittanceDetails.home.title">Remittance Details</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="mulyaaApp.remittanceDetails.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="mulyaaApp.remittanceDetails.home.createLabel">Create new Remittance Details</Translate>
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
          {remittanceDetailsList && remittanceDetailsList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="mulyaaApp.remittanceDetails.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('paymentType')}>
                    <Translate contentKey="mulyaaApp.remittanceDetails.paymentType">Payment Type</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('onlinePgType')}>
                    <Translate contentKey="mulyaaApp.remittanceDetails.onlinePgType">Online Pg Type</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('pgTxnId')}>
                    <Translate contentKey="mulyaaApp.remittanceDetails.pgTxnId">Pg Txn Id</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('pgStatus')}>
                    <Translate contentKey="mulyaaApp.remittanceDetails.pgStatus">Pg Status</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('offlineTxnId')}>
                    <Translate contentKey="mulyaaApp.remittanceDetails.offlineTxnId">Offline Txn Id</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('offlineTxnDetails')}>
                    <Translate contentKey="mulyaaApp.remittanceDetails.offlineTxnDetails">Offline Txn Details</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('offlineTxnGivenBy')}>
                    <Translate contentKey="mulyaaApp.remittanceDetails.offlineTxnGivenBy">Offline Txn Given By</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('offlineTxnClearingStatus')}>
                    <Translate contentKey="mulyaaApp.remittanceDetails.offlineTxnClearingStatus">Offline Txn Clearing Status</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('remittanceDate')}>
                    <Translate contentKey="mulyaaApp.remittanceDetails.remittanceDate">Remittance Date</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('remittanceInitTime')}>
                    <Translate contentKey="mulyaaApp.remittanceDetails.remittanceInitTime">Remittance Init Time</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('remittanceUpdateTime')}>
                    <Translate contentKey="mulyaaApp.remittanceDetails.remittanceUpdateTime">Remittance Update Time</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('paymentStatus')}>
                    <Translate contentKey="mulyaaApp.remittanceDetails.paymentStatus">Payment Status</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createdOn')}>
                    <Translate contentKey="mulyaaApp.remittanceDetails.createdOn">Created On</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('createdBy')}>
                    <Translate contentKey="mulyaaApp.remittanceDetails.createdBy">Created By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedOn')}>
                    <Translate contentKey="mulyaaApp.remittanceDetails.updatedOn">Updated On</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('updatedBy')}>
                    <Translate contentKey="mulyaaApp.remittanceDetails.updatedBy">Updated By</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="mulyaaApp.remittanceDetails.farmer">Farmer</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {remittanceDetailsList.map((remittanceDetails, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`${match.url}/${remittanceDetails.id}`} color="link" size="sm">
                        {remittanceDetails.id}
                      </Button>
                    </td>
                    <td>
                      <Translate contentKey={`mulyaaApp.PaymentType.${remittanceDetails.paymentType}`} />
                    </td>
                    <td>
                      <Translate contentKey={`mulyaaApp.PGType.${remittanceDetails.onlinePgType}`} />
                    </td>
                    <td>{remittanceDetails.pgTxnId}</td>
                    <td>{remittanceDetails.pgStatus}</td>
                    <td>{remittanceDetails.offlineTxnId}</td>
                    <td>{remittanceDetails.offlineTxnDetails}</td>
                    <td>{remittanceDetails.offlineTxnGivenBy}</td>
                    <td>{remittanceDetails.offlineTxnClearingStatus}</td>
                    <td>{remittanceDetails.remittanceDate}</td>
                    <td>
                      {remittanceDetails.remittanceInitTime ? (
                        <TextFormat type="date" value={remittanceDetails.remittanceInitTime} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      {remittanceDetails.remittanceUpdateTime ? (
                        <TextFormat type="date" value={remittanceDetails.remittanceUpdateTime} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>
                      <Translate contentKey={`mulyaaApp.PaymentStatus.${remittanceDetails.paymentStatus}`} />
                    </td>
                    <td>
                      {remittanceDetails.createdOn ? (
                        <TextFormat type="date" value={remittanceDetails.createdOn} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{remittanceDetails.createdBy}</td>
                    <td>
                      {remittanceDetails.updatedOn ? (
                        <TextFormat type="date" value={remittanceDetails.updatedOn} format={APP_LOCAL_DATE_FORMAT} />
                      ) : null}
                    </td>
                    <td>{remittanceDetails.updatedBy}</td>
                    <td>
                      {remittanceDetails.farmer ? (
                        <Link to={`user/${remittanceDetails.farmer.id}`}>{remittanceDetails.farmer.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`${match.url}/${remittanceDetails.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`${match.url}/${remittanceDetails.id}/edit`}
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
                          to={`${match.url}/${remittanceDetails.id}/delete`}
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
                <Translate contentKey="mulyaaApp.remittanceDetails.home.notFound">No Remittance Details found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default RemittanceDetails;
